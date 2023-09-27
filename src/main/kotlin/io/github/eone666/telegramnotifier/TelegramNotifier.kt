package io.github.eone666.telegramnotifier

import io.github.eone666.telegramnotifier.listeners.PlayerJoin
import io.github.eone666.telegramnotifier.listeners.PlayerQuit
import io.github.eone666.telegramnotifier.features.Notifications
import io.github.eone666.telegramnotifier.features.PlayersList
import io.github.eone666.telegramnotifier.utils.Config
import org.bukkit.plugin.java.JavaPlugin
import com.elbekd.bot.Bot
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents

class TelegramNotifier : JavaPlugin() {
    lateinit var config: Config
    lateinit var tg: Bot
    lateinit var notifications: Notifications
    lateinit var playersList: PlayersList

    override fun onEnable() {
        instance = this

        config = Config()
        saveDefaultConfig()

        tg = Bot.createPolling(token = config.token)
        tg.start()

        notifications = Notifications()
        playersList = PlayersList()

        //init features
        playersList.init()

        //register events
        server.pluginManager.registerSuspendingEvents(PlayerJoin(), this)
        server.pluginManager.registerSuspendingEvents(PlayerQuit(), this)

        logger.info("Started successfully")
    }

    override fun onDisable() {
        logger.info("Shutting down...")

        tg.stop()

        //disable features
        playersList.clear()
    }

    companion object {
        lateinit var instance: TelegramNotifier
    }
}

val pluginInstance: TelegramNotifier by lazy { TelegramNotifier.instance }