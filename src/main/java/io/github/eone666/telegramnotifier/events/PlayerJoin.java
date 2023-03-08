package io.github.eone666.telegramnotifier.events;

import io.github.eone666.telegramnotifier.TelegramNotifier.Method;
import io.github.eone666.telegramnotifier.utils.Telegram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final Telegram _tg;

    public PlayerJoin(Telegram tg) {
        _tg = tg;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        if (_tg._method == Method.EDIT || _tg._method == Method.BOTH) {
            _tg.AddPlayer(playerName);
        }
        if (_tg._method == Method.SEND || _tg._method == Method.BOTH) {
            _tg.SendMessage(String.format("%s has joined the game", playerName));
        }
    }
}
