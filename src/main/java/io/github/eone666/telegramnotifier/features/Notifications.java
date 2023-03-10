package io.github.eone666.telegramnotifier.features;

import io.github.eone666.telegramnotifier.TelegramNotifier;
import org.bukkit.entity.Player;

public class Notifications {

    private TelegramNotifier plugin = null;

    public Notifications (TelegramNotifier pluginInstance) {
        plugin = pluginInstance;
    }

     private void sendMessage (String message) {

            String text = message;

            if (plugin.config.getIsNotificationsPrefixEnabled()) {

                text = plugin.config.getNotificationsPrefixText() + message;

            }

            plugin.tg.sendMessage(plugin.config.getChatId(), text);
    }

    public void join (Player player) {
        if(plugin.config.getIsNotificationsEnabled()){
            this.sendMessage(String.format("%s has join the game", player.getName()));
        }
    }

    public void quit (Player player) {
        if(plugin.config.getIsNotificationsEnabled()){
            this.sendMessage(String.format("%s has left the game", player.getName()));
        }
    }

}
