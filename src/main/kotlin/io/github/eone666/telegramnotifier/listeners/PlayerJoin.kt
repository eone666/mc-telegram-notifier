package io.github.eone666.telegramnotifier.listeners

import io.github.eone666.telegramnotifier.TelegramNotifier
import io.github.eone666.telegramnotifier.features.notifications.NotificationTypes
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin(private val plugin: TelegramNotifier) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        plugin.notifications.send(NotificationTypes.JOIN,event.player)
        plugin.playersList.add(event.player)
    }
}