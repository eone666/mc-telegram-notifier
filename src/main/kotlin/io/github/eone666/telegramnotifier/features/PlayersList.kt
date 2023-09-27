package io.github.eone666.telegramnotifier.features

import com.elbekd.bot.model.toChatId
import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.entity.Player
import java.util.stream.Collectors
import com.elbekd.bot.types.ParseMode.Markdown
import com.github.shynixn.mccoroutine.bukkit.launch

class PlayersList() {
    private val players: MutableCollection<Player> = HashSet()
    private var text: String = ""
    private fun buildText() {
        val playersCount = players.size
        val playerNames = players.stream().map { obj: Player -> obj.name }.collect(Collectors.joining("\n"))

        text = if (playersCount == 0) "No players online" else "Players online:\n$playerNames"

        if (pluginInstance.config.isPlayersListHeaderEnabled) {
            text = "${pluginInstance.config.playersListHeaderText}\n$text"
        }

        if (pluginInstance.config.isPlayersListFooterEnabled) {
            text = "$text\n\n${pluginInstance.config.playersListFooterText}"
        }
    }

    private suspend fun sendNewMessageAndPin() {
        try {
            val response = pluginInstance.tg.sendMessage(
                chatId = pluginInstance.config.chatId.toChatId(),
                disableNotification = pluginInstance.config.isNotificationsSendSilently,
                text = text,
                parseMode = Markdown,
                disableWebPagePreview = true
            )
            pluginInstance.config.playersListMessageId = response.messageId
            pluginInstance.logger.info("New message sent")
        } catch (err: Throwable) {
            pluginInstance.logger.warning(err.message)
        }

        try {
            pluginInstance.tg.pinChatMessage(
                chatId = pluginInstance.config.chatId.toChatId(),
                disableNotification = pluginInstance.config.isNotificationsSendSilently,
                messageId = pluginInstance.config.playersListMessageId
            )
            pluginInstance.logger.info("Message pinned")
        } catch (err: Throwable) {
            pluginInstance.logger.warning(err.message)
        }
    }

    private suspend fun editMessage() {
        try {
            pluginInstance.tg.editMessageText(
                chatId = pluginInstance.config.chatId.toChatId(),
                messageId = pluginInstance.config.playersListMessageId,
                text = text,
                parseMode = Markdown,
                disableWebPagePreview = true
            )
        } catch (err: Throwable) {
            pluginInstance.logger.warning(err.message)
            sendNewMessageAndPin()
        }

    }

    private suspend fun updateMessage() {
        buildText()
        if (pluginInstance.config.playersListMessageId == 0.toLong()) {
            sendNewMessageAndPin()
        } else {
            editMessage()
        }
    }

    suspend fun add(player: Player) {
        if (pluginInstance.config.isPlayersListEnabled) {
            players.add(player)
            updateMessage()
        }
    }

    suspend fun remove(player: Player) {
        if (pluginInstance.config.isPlayersListEnabled) {
            players.remove(player)
            updateMessage()
        }
    }

    fun init() {
        if (pluginInstance.config.isPlayersListEnabled) {
            pluginInstance.launch {
                updateMessage()
            }
        }
    }

    fun clear() {
        if (pluginInstance.config.isPlayersListEnabled) {
            players.clear()
            pluginInstance.launch {
                updateMessage()
            }
        }
    }
}
