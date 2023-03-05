package io.github.eone666.telegramjoinnotifications.events;

import io.github.eone666.telegramjoinnotifications.utils.Telegram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private Telegram _tg = null;
    public PlayerQuit(Telegram tg){
        _tg = tg;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        _tg.SendMessage(String.format("%s has left the game",playerName ));
    }
}
