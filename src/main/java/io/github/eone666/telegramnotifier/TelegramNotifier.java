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

    public final String message = "Players+online:%0A";

    @Override
    public void onEnable() {

        saveDefaultConfig();

        boolean isEnabled = getConfig().getBoolean("enabled");

        if (isEnabled) {
            String token = getConfig().getString("token");
            String chatId = getConfig().getString("chatId");
            Method method = Method.valueOf(getConfig().getString("method")); // can be either edit or send
            String prefix = getConfig().getString("prefix");
            int pinned = getConfig().getInt("pinnedId");

            //put behind feature select flag in config
            if (method == Method.EDIT) {
                int result = InitMessage(token, chatId, pinned);
                if (pinned == 0) {
                    pinned = result;
                    getConfig().set("pinnedId", pinned);
                    saveConfig();
                }
            }

            Telegram tg = new Telegram(this, token, chatId, prefix, pinned, method);

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

    private int InitMessage(String token, String chatId, int pinned) {
        var client = HttpClient.newHttpClient();
        String fullMessage = message + "No+players+online";

        URI uri;
        if (pinned == 0) {
            uri = URI.create(String.format(
                    "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                    token,
                    chatId,
                    fullMessage
            ));
        } else {
            uri = URI.create(String.format("https://api.telegram.org/bot%s/editMessageText?chat_id=%s&message_id=%s&text=%s",
                    token,
                    chatId,
                    pinned,
                    fullMessage
            ));
        }

        var request = HttpRequest.newBuilder().uri(uri).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String res = response.body();

            String messageIdString = res.substring(res.indexOf("message_id") + 12, res.indexOf("from") - 2);

            return Integer.parseInt(messageIdString);
        } catch (Throwable err) {
            getLogger().info(err.getMessage());
            return 0;
        }
    }

    public enum Method {
        EDIT,
        SEND
    }
}
