package io.github.eone666.telegramnotifier;

import io.github.eone666.telegramnotifier.events.PlayerJoin;
import io.github.eone666.telegramnotifier.events.PlayerQuit;
import io.github.eone666.telegramnotifier.utils.Telegram;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class TelegramNotifier extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        boolean isEnabled = getConfig().getBoolean("enabled");

        if(isEnabled) {

            String token = getConfig().getString("token");
            String chatId = getConfig().getString("chatId");
            String prefix = getConfig().getString("prefix");
            int pinned = getConfig().getInt("pinnedId");

            if (pinned == 0) {
                pinned = InitMessage(token, chatId);
            }

            Telegram tg = new Telegram(this, token, chatId, prefix, pinned);

            getServer().getPluginManager().registerEvents(new PlayerJoin(tg), this);
            getServer().getPluginManager().registerEvents(new PlayerQuit(tg), this);

            getLogger().info("Events registered");

        }

        getLogger().info("Started successfully");

    }

    @Override
    public void onDisable() {
         getLogger().info("Shutting down...");
    }

    private int InitMessage (String token, String chatId) {
        var client = HttpClient.newHttpClient();

        String fullMessage = "Hello!+Here+is+a+list+of+players+online:%0ANo+players+online";

        var request = HttpRequest.newBuilder()
                .uri(
                        URI.create(String.format(
                                "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                                token,
                                chatId,
                                fullMessage
                        ))
                )
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // get response as json and store message_id
            // then use message_id to pin message
            String res = response.body();

            getLogger().info(res);

            String messageIdString = res.substring(res.indexOf("message_id") + 12, res.indexOf("from") - 2);

            getLogger().info(messageIdString);

            int messageId = Integer.parseInt(messageIdString);


            getConfig().set("pinnedId", messageId);

            saveConfig();

            return messageId;
        } catch (Throwable err) {
            getLogger().info(err.getMessage());
            return 0;
        }
    }
}
