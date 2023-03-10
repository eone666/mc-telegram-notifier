package io.github.eone666.telegramnotifier;

import io.github.eone666.telegramnotifier.events.PlayerJoin;
import io.github.eone666.telegramnotifier.events.PlayerQuit;
import io.github.eone666.telegramnotifier.features.Notifications;
import io.github.eone666.telegramnotifier.features.PlayersList;
import io.github.eone666.telegramnotifier.utils.Config;
import io.github.eone666.telegramnotifier.utils.telegram.Telegram;
import org.bukkit.plugin.java.JavaPlugin;

public final class TelegramNotifier extends JavaPlugin {

    public Config config = new Config(this);
    public Telegram tg = new Telegram(config.getToken());
    public Notifications notifications = new Notifications(this);
    public PlayersList playersList = new PlayersList(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        //init features
        playersList.init();
        //register events
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);

        getLogger().info("Started successfully");
    }

    @Override
    public void onDisable() {
         getLogger().info("Shutting down...");
         //disable features
         playersList.disable();
    }
}
