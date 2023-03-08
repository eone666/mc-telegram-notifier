package io.github.eone666.telegramnotifier;

import io.github.eone666.telegramnotifier.events.PlayerJoin;
import io.github.eone666.telegramnotifier.events.PlayerQuit;
import io.github.eone666.telegramnotifier.utils.Telegram;
import org.bukkit.plugin.java.JavaPlugin;

public final class TelegramNotifier extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        String token = getConfig().getString("token");

        Telegram tg = new Telegram(token);

        getServer().getPluginManager().registerEvents(new PlayerJoin(this,tg), this);

        getServer().getPluginManager().registerEvents(new PlayerQuit(this,tg), this);

        getLogger().info("Started successfully");

    }

    @Override
    public void onDisable() {
         getLogger().info("Shutting down...");
    }
}
