package io.github.eone666.telegramjoinnotifications.events;

import io.github.eone666.telegramjoinnotifications.events.utils.Telegram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private Telegram _tg = null;
    public PlayerJoin(Telegram tg){
        _tg = tg;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        _tg.SendMessage(String.format("%s has joined the game",playerName ));
    }
}
