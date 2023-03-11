package io.github.eone666.telegramnotifier.utils

import io.github.eone666.telegramnotifier.TelegramNotifier

class Config(private val plugin: TelegramNotifier) {
    val token: String
        get() = plugin.getConfig().getString("token")!!
    val chatId: String
        get() = plugin.getConfig().getString("chatId")!!
    val isNotificationsEnabled: Boolean
        get() = plugin.getConfig().getBoolean("notifications.enabled")
    val isNotificationsPrefixEnabled: Boolean
        get() = plugin.getConfig().getBoolean("notifications.prefix.enabled")
    val notificationsPrefixText: String
        get() = plugin.getConfig().getString("notifications.prefix.text")!!
    val isPlayersListEnabled: Boolean
        get() = plugin.getConfig().getBoolean("playersList.enabled")
    var playersListMessageId: Int
        get() = plugin.getConfig().getInt("playersList.messageId")
        set(id) {
            plugin.getConfig()["playersList.messageId"] = id
            plugin.saveConfig()
        }
    val isPlayersListHeaderEnabled: Boolean
        get() = plugin.getConfig().getBoolean("playersList.header.enabled")
    val playersListHeaderText: String
        get() = plugin.getConfig().getString("playersList.header.text")!!
}