package io.github.eone666.telegramnotifier.features

import io.github.eone666.telegramnotifier.TelegramNotifier
import org.bukkit.entity.Player

class Notifications(private val plugin: TelegramNotifier) {
    private fun sendMessage(message: String) {
        var text = message
        if (plugin.config.isNotificationsPrefixEnabled) {
            text = plugin.config.notificationsPrefixText + message
        }
        plugin.tg.sendMessage(plugin.config.chatId, text)
    }

    fun join(player: Player) {
        if (plugin.config.isNotificationsEnabled) {
            sendMessage(String.format("%s has join the game", player.name))
        }
    }

    fun quit(player: Player) {
        if (plugin.config.isNotificationsEnabled) {
            sendMessage(String.format("%s has left the game", player.name))
        }
    }
}