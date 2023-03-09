package io.github.eone666.telegramnotifier.utils;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Telegram {
    private Request request = null;

    public Telegram (String token) {
        request = new Request(String.format("https://api.telegram.org/bot%s/", token));
    }


    public JSONObject sendMessage(String chatId, String text) {
        HashMap data = new HashMap(){
            {
                put("chat_id", chatId);
                put("text", text);
            }
        };

        return request.postJson("sendMessage",data);
    }
    public JSONObject sendMessage(String chatId, String text, String parseMode) {
        HashMap data = new HashMap(){
            {
                put("chat_id", chatId);
                put("text", text);
                put("parse_mode",parseMode);
            }
        };

        return request.postJson("sendMessage",data);
    }

    public JSONObject editMessageText(String chatId, int messageId, String text) {

        HashMap data = new HashMap(){
            {
                put("chat_id", chatId);
                put("message_id", messageId);
                put("text", text);
            }
        };

        return request.postJson("editMessageText",data);

    }

    public JSONObject editMessageText(String chatId, int messageId, String text, String parseMode) {

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

    public JSONObject pinChatMessage(String chatId, int messageId) {

        HashMap data = new HashMap(){
            {
                put("chat_id", chatId);
                put("message_id", messageId);
            }
        };

        return request.postJson("pinChatMessage",data);

    }

}
