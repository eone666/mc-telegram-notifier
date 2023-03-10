package io.github.eone666.telegramnotifier.events;

import io.github.eone666.telegramnotifier.TelegramNotifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final TelegramNotifier plugin;
    public PlayerJoin(TelegramNotifier pluginInstance){
        plugin = pluginInstance;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.notifications.join(event.getPlayer());
        plugin.playersList.join(event.getPlayer());
    }
}
