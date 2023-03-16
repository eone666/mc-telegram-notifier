package io.github.eone666.telegramnotifier.utils

import io.github.eone666.telegramnotifier.TelegramNotifier
import org.bukkit.configuration.file.FileConfiguration

class Config(private val plugin: TelegramNotifier) {

    private val config: FileConfiguration = plugin.getConfig()
    val token: String
        get() = config.getString("token")!!
    val chatId: String
        get() = config.getString("chatId")!!
    val isNotificationsPlayerJoinEnabled: Boolean
        get() = config.getBoolean("notifications.playerJoin")
    val isNotificationsPlayerQuitEnabled: Boolean
        get() = config.getBoolean("notifications.playerQuit")
    val isNotificationsPrefixEnabled: Boolean
        get() = config.getBoolean("notifications.prefix.enabled")
    val notificationsPrefixText: String
        get() = config.getString("notifications.prefix.text")!!
    val isPlayersListEnabled: Boolean
        get() = config.getBoolean("playersList.enabled")

    val isNotificationsPlayerAdvancementEnabled: Boolean
        get() = config.getBoolean("notifications.playerAdvancement")

    var playersListMessageId: Int
        get() = config.getInt("playersList.messageId")
        set(id) {
            config.set("playersList.messageId", id)
            plugin.saveConfig()
        }
    val isPlayersListHeaderEnabled: Boolean
        get() = config.getBoolean("playersList.header.enabled")
    val playersListHeaderText: String
        get() = config.getString("playersList.header.text")!!

    val isPlayersListFooterEnabled: Boolean
        get() = config.getBoolean("playersList.footer.enabled")
    val playersListFooterText: String
        get() = config.getString("playersList.footer.text")!!
}