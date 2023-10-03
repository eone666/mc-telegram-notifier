package io.github.eone666.telegramnotifier.utils

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.configuration.file.FileConfiguration

class Config {
    private val config: FileConfiguration;
    init {
        pluginInstance.saveDefaultConfig()
        this.config = pluginInstance.getConfig()
    }

    fun save() {
        pluginInstance.saveConfig()
    }

    fun reload() {
        pluginInstance.reloadConfig()
    }

    var isPluginConfigured: Boolean
        get() = config.getBoolean("configured")
        set(value) {
            config.set("configured",value)
        }
    var token: String?
        get() = config.getString("token")
        set (token) {
            config.set("token", token)
        }
    var chatId: String?
        get() = config.getString("chatId")
        set (id) {
            config.set("chatId", id)
        }
    var isNotificationsPlayerJoinEnabled: Boolean
        get() = config.getBoolean("notifications.playerJoin")
        set(value) {
            config.set("notifications.playerJoin", value)
        }
    var isNotificationsPlayerQuitEnabled: Boolean
        get() = config.getBoolean("notifications.playerQuit")
        set(value) {
            config.set("notifications.playerQuit", value)
        }
    var isNotificationsPrefixEnabled: Boolean
        get() = config.getBoolean("notifications.prefix.enabled")
        set(value) {
            config.set("notifications.prefix.enabled", value)
        }
    var notificationsPrefixText: String?
        get() = config.getString("notifications.prefix.text")
        set(text) {
            config.set("notifications.prefix.text", text)
        }
    var isNotificationsSendSilently: Boolean
        get() = config.getBoolean("notifications.sendSilently")
        set(value) {
            config.set("notifications.sendSilently", value)
        }
    var isPlayersListEnabled: Boolean
        get() = config.getBoolean("playersList.enabled")
        set(value) {
            config.set("playersList.enabled", value)
        }

    var playersListMessageId: Long
        get() = config.getLong("playersList.messageId")
        set(id) {
            config.set("playersList.messageId", id)
        }
    val isPlayersListHeaderEnabled: Boolean
        get() = config.getBoolean("playersList.header.enabled")
    val playersListHeaderText: String?
        get() = config.getString("playersList.header.text")
    val isPlayersListFooterEnabled: Boolean
        get() = config.getBoolean("playersList.footer.enabled")
    val playersListFooterText: String?
        get() = config.getString("playersList.footer.text")
}