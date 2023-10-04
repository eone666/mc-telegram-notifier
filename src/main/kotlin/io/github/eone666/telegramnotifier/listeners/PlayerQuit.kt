package io.github.eone666.telegramnotifier.listeners

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuit() : Listener {
    @EventHandler
    suspend fun onPlayerQuit(event: PlayerQuitEvent) {
        if(pluginInstance.config.isPluginConfigured.boolean){
            pluginInstance.notifications.quit(event.player)
            pluginInstance.playersList.remove(event.player)
        }
    }
}