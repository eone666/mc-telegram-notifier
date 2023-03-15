package io.github.eone666.telegramnotifier.events

import io.github.eone666.telegramnotifier.TelegramNotifier
import io.github.eone666.telegramnotifier.features.notifications.NotificationTypes
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuit(private val plugin: TelegramNotifier) : Listener {
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        plugin.notifications.send(NotificationTypes.QUIT, event.player)
        plugin.playersList.remove(event.player)
    }
}