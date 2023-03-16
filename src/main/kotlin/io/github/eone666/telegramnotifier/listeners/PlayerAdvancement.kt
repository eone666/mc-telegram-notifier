package io.github.eone666.telegramnotifier.listeners

import io.github.eone666.telegramnotifier.TelegramNotifier
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAdvancementDoneEvent

class PlayerAdvancement(private val plugin: TelegramNotifier) : Listener {
    @EventHandler(ignoreCancelled = true)
    fun onPlayerAdvancementDone(event: PlayerAdvancementDoneEvent) {
        if (event.advancement.display?.doesAnnounceToChat() == true) {
            plugin.notifications.sendAdvancement(event.player, event.advancement)
        }
    }
}