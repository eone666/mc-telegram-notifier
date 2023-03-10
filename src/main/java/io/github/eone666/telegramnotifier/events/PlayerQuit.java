package io.github.eone666.telegramnotifier.events;

import io.github.eone666.telegramnotifier.TelegramNotifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final TelegramNotifier plugin;
    public PlayerQuit(TelegramNotifier pluginInstance){
        plugin = pluginInstance;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.notifications.quit(event.getPlayer());
        plugin.playersList.quit(event.getPlayer());
    }
}
