package io.github.eone666.telegramnotifier.features

import com.elbekd.bot.model.toChatId
import com.github.shynixn.mccoroutine.bukkit.launch
import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.entity.Player

class Notifications() {

    private val config = pluginInstance.config
    private suspend fun sendMessage(message: String) {
        var text = message
        if (config.isNotificationsPrefixEnabled.boolean) {
            text = config.notificationsPrefixText.string + message
        }
        try {
            pluginInstance.bot?.tg?.sendMessage(
                chatId = config.chatId.string!!.toChatId(),
                text = text,
                disableNotification = config.isNotificationsSilentModeEnabled.boolean,
                disableWebPagePreview = true
            )
        } catch (err: Throwable) {
            pluginInstance.logger.warning(err.message)
        }

    }

    fun join(player: Player){
        if (config.isNotificationsPlayerJoinEnabled.boolean) {
            pluginInstance.launch {
                sendMessage(String.format("%s has join the game", player.name))
            }
        }
    }

    fun quit(player: Player){
        if (config.isNotificationsPlayerQuitEnabled.boolean) {
            pluginInstance.launch {
                sendMessage(String.format("%s has left the game", player.name))
            }
        }
    }
}