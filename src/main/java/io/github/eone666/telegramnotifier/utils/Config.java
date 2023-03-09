package io.github.eone666.telegramnotifier.utils;

import io.github.eone666.telegramnotifier.TelegramNotifier;

public class Config {

    TelegramNotifier plugin = null;
    public Config(TelegramNotifier pluginInstance) {

        plugin = pluginInstance;

    }

    public String getToken(){
        return plugin.getConfig().getString("token");
    }
    public String getChatId(){
        return plugin.getConfig().getString("chatId");
    }
    public boolean getIsNotificationsEnabled(){
        return plugin.getConfig().getBoolean("notifications.enabled");
    }
    public boolean getIsNotificationsPrefixEnabled(){
        return plugin.getConfig().getBoolean("notifications.prefix.enabled");
    }
    public String getNotificationsPrefixText(){
        return plugin.getConfig().getString("notifications.prefix.text");
    }
    public boolean getIsPlayersListEnabled(){
        return plugin.getConfig().getBoolean("playersList.enabled");
    }
    public int getPlayersListMessageId(){
        return plugin.getConfig().getInt("playersList.messageId");
    }
    public boolean getIsPlayersListHeaderEnabled(){
        return plugin.getConfig().getBoolean("playersList.header.enabled");
    }
    public String getPlayersListHeaderText(){
        return plugin.getConfig().getString("playersList.header.text");
    }

    public void setPlayersListMessageId (int id) {
        plugin.getConfig().set("playersList.messageId", id);
        plugin.saveConfig();
    }

}
