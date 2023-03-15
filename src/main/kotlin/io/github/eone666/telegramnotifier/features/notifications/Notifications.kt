package io.github.eone666.telegramnotifier.features.notifications

import io.github.eone666.telegramnotifier.TelegramNotifier
import org.bukkit.entity.Player

class Notifications(private val plugin: TelegramNotifier) {
    private fun sendMessage(message: String) {
        var text = message
        if (plugin.config.isNotificationsPrefixEnabled) {
            text = plugin.config.notificationsPrefixText + message
        }
        plugin.tg.sendMessage(plugin.config.chatId, text,false, null)
    }

    fun send(type: NotificationTypes, player: Player){
        when (type) {
            NotificationTypes.JOIN -> {
                if (plugin.config.isNotificationsPlayerJoinEnabled) {
                    sendMessage(String.format("%s has join the game", player.name))
                }
            }
            NotificationTypes.QUIT -> {
                if (plugin.config.isNotificationsPlayerQuitEnabled) {
                    sendMessage(String.format("%s has left the game", player.name))
                }
            }
        }
    }
}