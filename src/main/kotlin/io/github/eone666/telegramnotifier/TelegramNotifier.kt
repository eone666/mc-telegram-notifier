package io.github.eone666.telegramnotifier

import io.github.eone666.telegramnotifier.listeners.PlayerJoin
import io.github.eone666.telegramnotifier.listeners.PlayerQuit
import io.github.eone666.telegramnotifier.features.notifications.Notifications
import io.github.eone666.telegramnotifier.features.PlayersList
import io.github.eone666.telegramnotifier.listeners.PlayerAdvancement
import io.github.eone666.telegramnotifier.utils.Config
import io.github.eone666.telegramnotifier.utils.telegram.Telegram
import org.bukkit.plugin.java.JavaPlugin

class TelegramNotifier : JavaPlugin() {
    val config = Config()
    val tg = Telegram(config.token)
    val notifications = Notifications()
    val playersList = PlayersList()
    override fun onEnable() {
        instance = this

        saveDefaultConfig()
        //init features
        playersList.init()
        //register events
        server.pluginManager.registerEvents(PlayerJoin(), this)
        server.pluginManager.registerEvents(PlayerQuit(), this)
        server.pluginManager.registerEvents(PlayerAdvancement(), this)
        getLogger().info("Started successfully")
    }

    override fun onDisable() {
        getLogger().info("Shutting down...")
        //disable features
        playersList.clear()
    }

    companion object {
        lateinit var instance: TelegramNotifier
    }
}

val pluginInstance: TelegramNotifier by lazy { TelegramNotifier.instance }