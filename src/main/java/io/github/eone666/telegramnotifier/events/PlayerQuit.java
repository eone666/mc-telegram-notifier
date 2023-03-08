package io.github.eone666.telegramnotifier.events;

import io.github.eone666.telegramnotifier.TelegramNotifier;
import io.github.eone666.telegramnotifier.utils.Telegram;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private Telegram tg = null;
    private TelegramNotifier plugin = null;
    public PlayerQuit(TelegramNotifier pluginInstance, Telegram tgInstance){
        plugin = pluginInstance;
        tg = tgInstance;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        FileConfiguration config = plugin.getConfig();

        String chatId = plugin.getConfig().getString("chatId");
        String playerName = event.getPlayer().getName();

        boolean isNotificationsEnabled = plugin.getConfig().getBoolean("notifications.enabled");

        if(isNotificationsEnabled) {

            boolean isPrefixEnabled = config.getBoolean("notifications.prefix.enabled");

            String baseMessage = String.format("%s has left the game",playerName );

            String message = baseMessage;

            if (isPrefixEnabled) {

                String prefix = config.getString("notifications.prefix.text");

                message = prefix + baseMessage;

            }

            tg.SendMessage(chatId, message);
        }
    }
}
