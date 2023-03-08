package io.github.eone666.telegramnotifier.utils;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.http.HttpResponse;
import java.util.HashMap;

public class Telegram {
    private Request request = null;

    public Telegram (String token) {
        request = new Request(String.format("https://api.telegram.org/bot%s/", token));
    }


    public JSONObject sendMessage(int chatId, String text) {
        HashMap data = new HashMap(){
            {
                put("chat_id", chatId);
                put("text", text);
            }
        };

        return request.postJson("sendMessage",data);
    }
    public JSONObject sendMessage(int chatId, String text, String parseMode) {
        HashMap data = new HashMap(){
            {
                put("chat_id", chatId);
                put("text", text);
                put("parse_mode",parseMode);
            }
        };

        return request.postJson("sendMessage",data);
    }

    public JSONObject editMessageText(int chatId, int messageId, String text) {

        HashMap data = new HashMap(){
            {
                put("chat_id", chatId);
                put("message_id", messageId);
                put("text", text);
            }
        };

        return request.postJson("editMessageText",data);

    }

    public JSONObject editMessageText(int chatId, int messageId, String text, String parseMode) {

        HashMap data = new HashMap(){
            {
                put("chat_id", chatId);
                put("message_id", messageId);
                put("text", text);
                put("parse_mode",parseMode);
            }
        };

        return request.postJson("editMessageText",data);

    }

    public JSONObject pinChatMessage(int chatId, int messageId) {

        HashMap data = new HashMap(){
            {
                put("chat_id", chatId);
                put("message_id", messageId);
            }
        };

        return request.postJson("pinChatMessage",data);

    }

}
