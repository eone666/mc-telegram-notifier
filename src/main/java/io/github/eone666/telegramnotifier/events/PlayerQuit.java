package io.github.eone666.telegramnotifier.events;

import io.github.eone666.telegramnotifier.TelegramNotifier;
import io.github.eone666.telegramnotifier.utils.Telegram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private Telegram tg = null;
    private TelegramNotifier plugin = null;
    public PlayerQuit(TelegramNotifier pluginInstance){
        plugin = pluginInstance;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(plugin.config.getIsNotificationsEnabled()) {
            plugin.notifications.quit(event.getPlayer());
        }

        if(plugin.config.getIsPlayersListEnabled()) {
            plugin.playersList.quit(event.getPlayer());
        }
    }
}
