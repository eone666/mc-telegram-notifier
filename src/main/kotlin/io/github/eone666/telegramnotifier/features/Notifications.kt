package io.github.eone666.telegramnotifier.features

import com.elbekd.bot.model.toChatId
import com.github.shynixn.mccoroutine.bukkit.launch
import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.entity.Player

class Notifications {

    private val config = pluginInstance.config
    private suspend fun sendMessage(message: String) {
        var text = message
        if (config.isNotificationsPrefixEnabled.get()) {
            text = config.notificationsPrefixText.get() + message
        }
        try {
            pluginInstance.bot?.tg?.sendMessage(
                chatId = config.chatId.get().toChatId(),
                text = text,
                disableNotification = config.isNotificationsSilentModeEnabled.get(),
                disableWebPagePreview = true
            )
        } catch (err: Throwable) {
            pluginInstance.logger.warning(err.message)
        }

    }

    fun join(player: Player){
        if (config.isNotificationsPlayerJoinEnabled.get()) {
            pluginInstance.launch {
                sendMessage("${player.name} has join the game")
            }
        }
    }

    fun quit(player: Player){
        if (config.isNotificationsPlayerQuitEnabled.get()) {
            pluginInstance.launch {
                sendMessage("${player.name} has left the game")
            }
        }
    }
}