package io.github.eone666.telegramnotifier

import org.bukkit.plugin.java.JavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents


import io.github.eone666.telegramnotifier.listeners.PlayerJoin
import io.github.eone666.telegramnotifier.listeners.PlayerQuit

import io.github.eone666.telegramnotifier.features.Notifications
import io.github.eone666.telegramnotifier.features.PlayersList
import io.github.eone666.telegramnotifier.features.OneTimePasswordForSender

import io.github.eone666.telegramnotifier.telegram.Bot

import io.github.eone666.telegramnotifier.commands.Setup as SetupCommand
import io.github.eone666.telegramnotifier.commands.CancelSetup as CancelSetupCommand
import io.github.eone666.telegramnotifier.commands.Config as ConfigCommand

class TelegramNotifier : JavaPlugin() {
    lateinit var config: Config
    var bot: Bot? = null
    lateinit var oneTimePasswordForSender: OneTimePasswordForSender
    lateinit var notifications: Notifications
    lateinit var playersList: PlayersList

    fun initFeatures() {
        notifications = Notifications()
        playersList = PlayersList()

        //init features
        playersList.update()
    }

    override fun onEnable() {
        instance = this

        config = Config()

        if (config.isPluginConfigured.boolean) {
            logger.info("Plugin configured, starting bot...")
            bot = Bot(config.token.string!!)
            initFeatures()
        }

        //register events
        server.pluginManager.registerSuspendingEvents(PlayerJoin(), this)
        server.pluginManager.registerSuspendingEvents(PlayerQuit(), this)

        //register minecraft commands
        getCommand("setup")?.setExecutor(SetupCommand())
        getCommand("cancelsetup")?.setExecutor(CancelSetupCommand())
        getCommand("config")?.tabCompleter = ConfigCommand()

        logger.info("Started successfully")
    }

    override fun onDisable() {
        logger.info("Shutting down...")
        bot?.stop()

        // disable features
        playersList.clear()
    }

    companion object {
        lateinit var instance: TelegramNotifier
    }
}

val pluginInstance: TelegramNotifier by lazy { TelegramNotifier.instance }