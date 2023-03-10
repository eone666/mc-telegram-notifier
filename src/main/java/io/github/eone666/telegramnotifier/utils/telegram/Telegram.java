package io.github.eone666.telegramnotifier.utils.telegram;

import io.github.eone666.telegramnotifier.utils.Request;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Telegram {
    final private Request request;

    public Telegram (String token) {
        request = new Request(String.format("https://api.telegram.org/bot%s/", token));
    }

    private void handleErrors (JSONObject response) {
        boolean isOk = Boolean.parseBoolean(response.get("ok").toString());

        if(!isOk){
            int errorCode = Integer.parseInt(response.get("error_code").toString());
            String description = response.get("description").toString();
            Bukkit.getLogger().warning(String.format("Error code: %s", errorCode));
            Bukkit.getLogger().warning(String.format("Description: %s",description));
        }
    }

    public JSONObject sendMessage(String chatId, String text) {
        Map<String, Object> data = new HashMap<>(){
            {
                put("chat_id", chatId);
                put("text", text);
            }
        };

        JSONObject response =  request.postJson("sendMessage",data);

        handleErrors(response);

        return response;
    }

    public JSONObject sendMessage(String chatId, String text, ParseMode parseMode) {
        Map<String, Object> data = new HashMap<>(){
            {
                put("chat_id", chatId);
                put("text", text);
                put("parse_mode",parseMode.toString());
            }
        };

        JSONObject response = request.postJson("sendMessage",data);

        handleErrors(response);

        return response;
    }

    public JSONObject editMessageText(String chatId, int messageId, String text) {

        Map<String, Object> data = new HashMap<>(){
            {
                put("chat_id", chatId);
                put("message_id", messageId);
                put("text", text);
            }
        };

        JSONObject response = request.postJson("editMessageText",data);

        handleErrors(response);

        return response;

    }

    public JSONObject editMessageText(String chatId, int messageId, String text, ParseMode parseMode) {

        Map<String, Object> data = new HashMap<>(){
            {
                put("chat_id", chatId);
                put("message_id", messageId);
                put("text", text);
                put("parse_mode",parseMode.toString());
            }
        };

        JSONObject response = request.postJson("editMessageText",data);

        handleErrors(response);

        return response;

    }

    public JSONObject pinChatMessage(String chatId, int messageId) {

        Map<String, Object> data = new HashMap<>(){
            {
                put("chat_id", chatId);
                put("message_id", messageId);
            }
        };

        JSONObject response = request.postJson("pinChatMessage",data);

        handleErrors(response);

        return response;

    }

}
