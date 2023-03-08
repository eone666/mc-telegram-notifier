package io.github.eone666.telegramnotifier.events;

import io.github.eone666.telegramnotifier.TelegramNotifier;
import io.github.eone666.telegramnotifier.features.Notifications;
import io.github.eone666.telegramnotifier.features.PlayersList;
import io.github.eone666.telegramnotifier.utils.Telegram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.stream.Collectors;

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
