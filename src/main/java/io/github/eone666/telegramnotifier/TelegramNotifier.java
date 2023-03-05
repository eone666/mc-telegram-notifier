package io.github.eone666.telegramnotifier;

import io.github.eone666.telegramnotifier.events.PlayerJoin;
import io.github.eone666.telegramnotifier.events.PlayerQuit;
import io.github.eone666.telegramnotifier.utils.Telegram;
import org.bukkit.plugin.java.JavaPlugin;

public final class TelegramNotifier extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        boolean isEnabled = getConfig().getBoolean("enabled");

        if(isEnabled) {

            String token = getConfig().getString("token");
            String chatId = getConfig().getString("chatId");
            String prefix = getConfig().getString("prefix");

            Telegram tg = new Telegram(this, token, chatId, prefix);

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
}
