package io.github.eone666.telegramnotifier.utils;

import io.github.eone666.telegramnotifier.TelegramNotifier;
import io.github.eone666.telegramnotifier.TelegramNotifier.Method;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Telegram {
    public final Method _method;
    private final TelegramNotifier _plugin;
    private final String _token;
    private final String _chatId;
    private final String _prefix;
    private final ArrayList<String> _players = new ArrayList<>();
    private final int _pinned;

    public Telegram(TelegramNotifier plugin, String token, String chatId, String prefix, int pinned, Method method) {
        _plugin = plugin;
        _token = token;
        _chatId = chatId;
        _prefix = prefix;
        _pinned = pinned;
        _method = method;
    }

    public void AddPlayer(String playerName) {
        _players.add(playerName);
        UpdateMessage();
    }

    public void RemovePlayer(String playerName) {
        _players.remove(playerName);
        UpdateMessage();
    }

    private void UpdateMessage() {
        try {

            var client = HttpClient.newHttpClient();

            String fullMessage = _plugin.message + (_players.size() == 0 ? "No+players+online" : String.format("%s", String.join("%0A ", _players)));

            var request = HttpRequest.newBuilder()
                    .uri(
                            URI.create(String.format("https://api.telegram.org/bot%s/editMessageText?chat_id=%s&message_id=%s&text=%s",
                                    _token,
                                    _chatId,
                                    _pinned,
                                    fullMessage
                            ))
                    )
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            _plugin.getLogger().info(response.body());

        } catch (Throwable err) {
            _plugin.getLogger().info(err.getMessage());
        }
    }

    public void SendMessage(String message) {

        try {

            var client = HttpClient.newHttpClient();

            String fullMessage = _prefix != null ?
                    _prefix.replace(' ', '+') + (message.replace(' ', '+')) :
                    message.replace(' ', '+');


            var request = HttpRequest.newBuilder()
                    .uri(
                            URI.create(String.format(
                                    "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                                    _token,
                                    _chatId,
                                    fullMessage
                            ))
                    )
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            _plugin.getLogger().info(response.body());

        } catch (Throwable err) {
            _plugin.getLogger().info(err.getMessage());
        }
    }
}
