package io.github.eone666.telegramnotifier.events

import io.github.eone666.telegramnotifier.TelegramNotifier
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin(private val plugin: TelegramNotifier) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        plugin.notifications.join(event.player)
        plugin.playersList.join(event.player)
    }
}