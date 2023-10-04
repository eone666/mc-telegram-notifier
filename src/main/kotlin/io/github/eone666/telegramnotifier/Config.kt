package io.github.eone666.telegramnotifier

import org.bukkit.configuration.file.FileConfiguration
import kotlin.jvm.Throws

@Suppress("UNCHECKED_CAST")
class ConfigItem<T>(val key: String, private val defaultValue: T) {
    private val config = pluginInstance.getConfig()

    @Throws(IllegalArgumentException::class) fun get(): T {
        val a: T = defaultValue
        when (a) {
            is String -> return config.getString(key, defaultValue as String) as T

            is Boolean -> {
                return config.getBoolean(key, defaultValue as Boolean) as T
            }

            is Int -> {
                return config.getInt(key, defaultValue as Int) as T
            }

            is Long -> {
                return config.getLong(key, defaultValue as Long) as T
            }

            else -> {
                throw IllegalArgumentException("Type not implemented for ConfigItem")
            }
        }
    }

    fun set(value: T?) {
        config.set(key, value)
        pluginInstance.saveConfig()
    }
}

class Config {
    private val config: FileConfiguration
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

    fun set(key: String, value: Any?){
        config.set(key, value)
    }

    val token = ConfigItem<String>("token", "123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11")
    val chatId = ConfigItem<String>("chatId", "0000000000000")
    val isPluginConfigured = ConfigItem<Boolean>("configured", false)
    var isNotificationsPlayerJoinEnabled = ConfigItem<Boolean>("notifications.playerJoin", false)
    var isNotificationsPlayerQuitEnabled = ConfigItem<Boolean>("notifications.playerQuit", false)
    var isNotificationsPrefixEnabled = ConfigItem<Boolean>("notifications.prefix.enabled", false)
    var notificationsPrefixText = ConfigItem<String>("notifications.prefix.text", "[Minecraft] ")
    var isNotificationsSilentModeEnabled = ConfigItem<Boolean>("notifications.silentMode", false)
    var isPlayersListEnabled = ConfigItem<Boolean>("playersList.enabled", false)
    var playersListMessageId = ConfigItem<Long>("playersList.messageId", 0L)
    val isPlayersListHeaderEnabled = ConfigItem<Boolean>("playersList.header.enabled", false)
    val playersListHeaderText = ConfigItem<String>("playersList.header.text", "Header\n")
    val isPlayersListFooterEnabled = ConfigItem<Boolean>("playersList.footer.enabled", false)
    val playersListFooterText = ConfigItem<String>("playersList.footer.text", "\nFooter")
}