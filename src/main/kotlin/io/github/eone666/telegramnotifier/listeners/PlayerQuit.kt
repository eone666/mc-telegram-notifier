package io.github.eone666.telegramnotifier.listeners

import io.github.eone666.telegramnotifier.features.notifications.NotificationTypes
import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuit() : Listener {
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        pluginInstance.notifications.send(NotificationTypes.QUIT, event.player)
        pluginInstance.playersList.remove(event.player)
    }
}