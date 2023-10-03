package io.github.eone666.telegramnotifier

import io.github.eone666.telegramnotifier.listeners.PlayerJoin
import io.github.eone666.telegramnotifier.listeners.PlayerQuit
import io.github.eone666.telegramnotifier.features.Notifications
import io.github.eone666.telegramnotifier.features.PlayersList
import io.github.eone666.telegramnotifier.utils.Config
import org.bukkit.plugin.java.JavaPlugin
import com.elbekd.bot.Bot
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import io.github.eone666.telegramnotifier.commands.minecraft.CancelSetup
import io.github.eone666.telegramnotifier.commands.minecraft.Setup as SetupCommandMinecraft
import io.github.eone666.telegramnotifier.commands.telegram.Setup as SetupCommandTelegram
import io.github.eone666.telegramnotifier.features.Setup
import io.github.eone666.telegramnotifier.commands.minecraft.PlayerJoin as PlayerJoinCommand
import io.github.eone666.telegramnotifier.commands.minecraft.PlayerQuit as PlayerQuitCommand
import io.github.eone666.telegramnotifier.commands.minecraft.PlayersList as PlayersListCommand
import io.github.eone666.telegramnotifier.commands.minecraft.SendSilently as SendSilentlyCommand

class TelegramNotifier : JavaPlugin() {
    lateinit var config: Config
    var tg: Bot? = null
    lateinit var setup: Setup
    lateinit var notifications: Notifications
    lateinit var playersList: PlayersList

    fun initBot(token: String){
        tg = Bot.createPolling(token = token)
        tg!!.start()

        //register telegram commands
        SetupCommandTelegram().init()
    }

    fun killBot() {
        tg?.stop()
        tg = null
    }

    fun initFeatures() {
        notifications = Notifications()
        playersList = PlayersList()

        //init features
        playersList.init()
    }

    override fun onEnable() {
        instance = this

        config = Config()

        config.init()

        setup = Setup()

        if(config.isPluginConfigured){
            initBot(config.token)
            initFeatures()
        }

        //register events
        server.pluginManager.registerSuspendingEvents(PlayerJoin(), this)
        server.pluginManager.registerSuspendingEvents(PlayerQuit(), this)

        //register minecraft commands
        getCommand("setup")?.setExecutor(SetupCommandMinecraft())
        getCommand("cancelsetup")?.setExecutor(CancelSetup())
        getCommand("playerjoin")?.setExecutor(PlayerJoinCommand())
        getCommand("playerquit")?.setExecutor(PlayerQuitCommand())
        getCommand("playerslist")?.setExecutor(PlayersListCommand())
        getCommand("sendsilently")?.setExecutor(SendSilentlyCommand())

        logger.info("Started successfully")
    }

    override fun onDisable() {
        logger.info("Shutting down...")

        killBot()

        //disable features
        playersList.clear()
    }

    companion object {
        lateinit var instance: TelegramNotifier
    }
}

val pluginInstance: TelegramNotifier by lazy { TelegramNotifier.instance }