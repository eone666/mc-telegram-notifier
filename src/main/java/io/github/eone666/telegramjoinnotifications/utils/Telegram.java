package io.github.eone666.telegramjoinnotifications.utils;

import io.github.eone666.telegramjoinnotifications.TelegramJoinNotifications;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Telegram {

    private TelegramJoinNotifications _plugin = null;
    private String _token = null;
    private String _chatId = null;
    private String _prefix = null;

    public Telegram (TelegramJoinNotifications plugin, String token, String chatId, String prefix) {
        _plugin = plugin;
        _token = token;
        _chatId = chatId;
        _prefix = prefix;
    }

    public void SendMessage (String message) {

        try {

            var client = HttpClient.newHttpClient();

            String fullMessage = _prefix != null ?
                        _prefix.replace(' ','+') + (message.replace(' ','+')):
                        message.replace(' ','+');

            
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

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            _plugin.getLogger().info(response.body().toString());

        } catch (Throwable err) {
            _plugin.getLogger().info(err.getMessage());
        }
    }
}
