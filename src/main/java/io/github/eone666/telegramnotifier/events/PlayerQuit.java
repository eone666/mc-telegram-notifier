package io.github.eone666.telegramnotifier.events;

import io.github.eone666.telegramnotifier.TelegramNotifier.Method;
import io.github.eone666.telegramnotifier.utils.Telegram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final Telegram _tg;

    public PlayerQuit(Telegram tg) {
        _tg = tg;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        if (_tg._method == Method.EDIT) {
            _tg.RemovePlayer(playerName);
        } else {
            _tg.SendMessage(String.format("%s has left the game", playerName));
        }
    }
}
