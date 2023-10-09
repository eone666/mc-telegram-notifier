package io.github.eone666.telegramnotifier

import org.bukkit.configuration.file.FileConfiguration
import java.io.File

@Suppress("UNCHECKED_CAST")
class ConfigItem<T>(val key: String, val defaultValue: T) {
    @Throws(IllegalArgumentException::class) fun get(): T {
        pluginInstance.reloadConfig()
        val config = pluginInstance.getConfig()

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
        pluginInstance.getConfig().set(key, value)
        pluginInstance.saveConfig()
    }
}

class Config {
    init {
        pluginInstance.saveDefaultConfig()
    }

    fun set(key: String, value: Any?){
        pluginInstance.getConfig().set(key, value)
        pluginInstance.saveConfig()
    }

    fun reset () {
        val configFile = File(pluginInstance.dataFolder, "config.yml")
        configFile.delete()
        pluginInstance.saveDefaultConfig()
    }

    val token = ConfigItem<String>("token", "123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11")
    val chatId = ConfigItem<String>("chatId", "0000000000000")
    val isPluginConfigured = ConfigItem<Boolean>("configured", false)
    var isNotificationsPlayerJoinEnabled = ConfigItem<Boolean>("notifications.playerJoin", false)
    var isNotificationsPlayerQuitEnabled = ConfigItem<Boolean>("notifications.playerQuit", false)
    var isNotificationsPrefixEnabled = ConfigItem<Boolean>("notifications.prefix.enabled", false)
    var notificationsPrefixText = ConfigItem<String>("notifications.prefix.text", "[Minecraft] ")
    var isNotificationsSilentModeEnabled = ConfigItem<Boolean>("notifications.silentMode", false)
    var isPlayerListEnabled = ConfigItem<Boolean>("playerList.enabled", false)
    var playerListMessageId = ConfigItem<Long>("playerList.messageId", 0L)
    val isPlayerListHeaderEnabled = ConfigItem<Boolean>("playerList.header.enabled", false)
    val playerListHeaderText = ConfigItem<String>("playerList.header.text",
        """
        IP: `127.0.0.1:25565`
        Website: [https://example.org](https://example.org)
        """.trimIndent() + "\n"
    )
    val isPlayerListFooterEnabled = ConfigItem<Boolean>("playerList.footer.enabled", false)
    val playerListFooterText = ConfigItem<String>("playerList.footer.text",
        "\n\n" + """
        Author: [Example](https://link.to/personal-page)
        """.trimIndent()
    )
}