package io.github.eone666.telegramnotifier.features

import com.elbekd.bot.model.toChatId
import com.github.shynixn.mccoroutine.bukkit.launch
import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.entity.Player

class Notifications() {
    private suspend fun sendMessage(message: String) {
        var text = message
        if (pluginInstance.config.isNotificationsPrefixEnabled) {
            text = pluginInstance.config.notificationsPrefixText + message
        }
        pluginInstance.tg.sendMessage(
                chatId = pluginInstance.config.chatId.toChatId(),
                text = text,
                disableNotification = pluginInstance.config.isNotificationsSendSilently,
                disableWebPagePreview = true
        )
    }

    fun join(player: Player){
        if (pluginInstance.config.isNotificationsPlayerJoinEnabled) {
            pluginInstance.launch {
                sendMessage(String.format("%s has join the game", player.name))
            }
        }
    }

    fun quit(player: Player){
        if (pluginInstance.config.isNotificationsPlayerQuitEnabled) {
            pluginInstance.launch {
                sendMessage(String.format("%s has left the game", player.name))
            }
        }
    }
}