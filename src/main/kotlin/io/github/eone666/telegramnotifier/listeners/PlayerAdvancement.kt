package io.github.eone666.telegramnotifier.listeners

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAdvancementDoneEvent

class PlayerAdvancement() : Listener {
    @EventHandler(ignoreCancelled = true)
    fun onPlayerAdvancementDone(event: PlayerAdvancementDoneEvent) {
        if (event.advancement.display?.doesAnnounceToChat() == true) {
            pluginInstance.notifications.advancement(event.player, event.advancement)
        }
    }
}