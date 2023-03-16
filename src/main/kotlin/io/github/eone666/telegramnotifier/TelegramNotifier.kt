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
    val config = Config(this)
    val tg = Telegram(config.token)
    val notifications = Notifications(this)
    val playersList = PlayersList(this)
    override fun onEnable() {
        saveDefaultConfig()
        //init features
        playersList.init()
        //register events
        server.pluginManager.registerEvents(PlayerJoin(this), this)
        server.pluginManager.registerEvents(PlayerQuit(this), this)
        server.pluginManager.registerEvents(PlayerAdvancement(this), this)
        getLogger().info("Started successfully")
    }

    override fun onDisable() {
        getLogger().info("Shutting down...")
        //disable features
        playersList.clear()
    }
}