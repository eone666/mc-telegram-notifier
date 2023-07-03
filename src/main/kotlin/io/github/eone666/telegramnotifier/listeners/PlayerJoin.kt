package io.github.eone666.telegramnotifier.listeners

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin() : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        pluginInstance.notifications.join(event.player)
        pluginInstance.playersList.add(event.player)
    }
}