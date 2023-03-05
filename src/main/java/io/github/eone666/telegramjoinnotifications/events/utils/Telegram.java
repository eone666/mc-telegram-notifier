package io.github.eone666.telegramjoinnotifications.events.utils;

import org.json.simple.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Telegram {
    private String _token = null;
    private String _chatId = null;
    private String _prefix = null;

    public Telegram (String token, String chatId, String prefix) {
        _token = token;
        _chatId = chatId;
        _prefix = prefix;
    }

    public void SendMessage (String message) {

        JSONObject json = new JSONObject();
        json.put("chat_id",_chatId);
        json.put("text",message);

        try {
            var client = HttpClient.newHttpClient();

            String fullMessage = String.format("[%s]",_prefix) + '+' + (message.replace(' ','+'));
            
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format( "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",_token,_chatId,fullMessage)))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());

        } catch (Throwable err) {
            System.out.println(err.getMessage());
        }
    }
}
