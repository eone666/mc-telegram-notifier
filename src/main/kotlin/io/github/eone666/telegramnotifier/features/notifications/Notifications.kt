package io.github.eone666.telegramnotifier.features.notifications

import io.github.eone666.telegramnotifier.pluginInstance
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.advancement.Advancement
import org.bukkit.entity.Player

class Notifications() {
    private fun sendMessage(message: String) {
        var text = message
        if (pluginInstance.config.isNotificationsPrefixEnabled) {
            text = pluginInstance.config.notificationsPrefixText + message
        }
        pluginInstance.tg.sendMessage(
                pluginInstance.config.chatId,
                pluginInstance.config.isNotificationsSendSilently,
                text, false, null
        )
    }

    fun send(type: NotificationTypes, player: Player){
        when (type) {
            NotificationTypes.JOIN -> {
                if (pluginInstance.config.isNotificationsPlayerJoinEnabled) {
                    sendMessage(String.format("%s has join the game", player.name))
                }
            }
            NotificationTypes.QUIT -> {
                if (pluginInstance.config.isNotificationsPlayerQuitEnabled) {
                    sendMessage(String.format("%s has left the game", player.name))
                }
            }
        }
    }
    fun sendAdvancement(player: Player, advancement: Advancement){
        if (pluginInstance.config.isNotificationsPlayerAdvancementEnabled) {
            val playerName = player.name
            val advancementName: String = PlainTextComponentSerializer.plainText().serialize(advancement.displayName())
            sendMessage("Player $playerName got advancement $advancementName")
        }
    }
}