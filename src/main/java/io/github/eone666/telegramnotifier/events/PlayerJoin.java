package io.github.eone666.telegramnotifier.events;

import io.github.eone666.telegramnotifier.TelegramNotifier;
import io.github.eone666.telegramnotifier.utils.telegram.Telegram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private Telegram tg = null;
    private TelegramNotifier plugin = null;
    public PlayerJoin(TelegramNotifier pluginInstance){
        plugin = pluginInstance;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.notifications.join(event.getPlayer());
        plugin.playersList.join(event.getPlayer());
    }
}
