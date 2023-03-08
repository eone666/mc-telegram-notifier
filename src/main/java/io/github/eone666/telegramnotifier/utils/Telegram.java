package io.github.eone666.telegramnotifier.utils;

import java.util.HashMap;

public class Telegram {
    private Request request = null;

    public Telegram (String token) {
        request = new Request(String.format("https://api.telegram.org/bot%s/", token));
    }

    public void SendMessage (String chatId, String text) {
        HashMap data = new HashMap(){
            {
                put("chat_id",chatId);
                put("text",text);
            }
        };

        request.postJson("sendMessage",data);
    }

}
