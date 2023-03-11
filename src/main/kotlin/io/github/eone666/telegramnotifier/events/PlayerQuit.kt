package io.github.eone666.telegramnotifier.events

import io.github.eone666.telegramnotifier.TelegramNotifier
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuit(private val plugin: TelegramNotifier) : Listener {
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        plugin.notifications.quit(event.player)
        plugin.playersList.quit(event.player)
    }
}