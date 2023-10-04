package io.github.eone666.telegramnotifier

import org.bukkit.configuration.file.FileConfiguration


class ConfigItem(
    val key: String
) {

    private val config = pluginInstance.getConfig()

    fun set (value: Any?) {
        config.set(key, value)
    }
    val string: String?
        get() {
            return config.getString(key)
        }
    val boolean: Boolean
        get() {
            return config.getBoolean(key)
        }
    val int: Int
        get() {
            return config.getInt(key)
        }
    val long: Long
        get() {
            return config.getLong(key)
        }
}

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

    val token = ConfigItem("token")
    val chatId = ConfigItem("chatId")
    val isPluginConfigured = ConfigItem("configured")
    var isNotificationsPlayerJoinEnabled = ConfigItem("notifications.playerJoin")
    var isNotificationsPlayerQuitEnabled = ConfigItem("notifications.playerQuit")
    var isNotificationsPrefixEnabled = ConfigItem("notifications.prefix.enabled")
    var notificationsPrefixText = ConfigItem("notifications.prefix.text")
    var isNotificationsSilentModeEnabled = ConfigItem("notifications.silentMode")
    var isPlayersListEnabled = ConfigItem("playersList.enabled")
    var playersListMessageId = ConfigItem("playersList.messageId")
    val isPlayersListHeaderEnabled = ConfigItem("playersList.header.enabled")
    val playersListHeaderText = ConfigItem("playersList.header.text")
    val isPlayersListFooterEnabled = ConfigItem("playersList.footer.enabled")
    val playersListFooterText = ConfigItem("playersList.footer.text")
}