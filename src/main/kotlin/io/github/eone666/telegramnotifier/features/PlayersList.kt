package io.github.eone666.telegramnotifier.features

import com.elbekd.bot.model.toChatId
import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.entity.Player
import java.util.stream.Collectors
import com.elbekd.bot.types.ParseMode.Markdown
import com.github.shynixn.mccoroutine.bukkit.launch

class PlayersList() {
    private val config = pluginInstance.config
    private val tg = pluginInstance.bot?.tg

    private val players: MutableCollection<Player> = HashSet()
    private var text: String = ""
    private fun buildText() {
        val playersCount = players.size
        val playerNames = players.stream().map { obj: Player -> obj.name }.collect(Collectors.joining("\n"))

        text = if (playersCount == 0) "No players online" else "Players online:\n$playerNames"

        if (config.isPlayersListHeaderEnabled.boolean) {
            text = "${config.playersListHeaderText}\n$text"
        }

        if (config.isPlayersListFooterEnabled.boolean) {
            text = "$text\n\n${config.playersListFooterText}"
        }
    }

    private suspend fun sendNewMessageAndPin() {
        try {
            val response = tg?.sendMessage(
                chatId = config.chatId.string!!.toChatId(),
                disableNotification = config.isNotificationsSilentModeEnabled.boolean,
                text = text,
                parseMode = Markdown,
                disableWebPagePreview = true
            )
            config.playersListMessageId.set(response!!.messageId)
            config.save()
            pluginInstance.logger.info("New message sent")
        } catch (err: Throwable) {
            pluginInstance.logger.warning(err.message)
        }

        try {
            tg?.pinChatMessage(
                chatId = config.chatId.string!!.toChatId(),
                disableNotification = config.isNotificationsSilentModeEnabled.boolean,
                messageId = config.playersListMessageId.long
            )
            pluginInstance.logger.info("Message pinned")
        } catch (err: Throwable) {
            pluginInstance.logger.warning(err.message)
        }
    }

    private suspend fun editMessage() {
        try {
            tg?.editMessageText(
                chatId = config.chatId.string!!.toChatId(),
                messageId = config.playersListMessageId.long,
                text = text,
                parseMode = Markdown,
                disableWebPagePreview = true
            )
        } catch (err: Throwable) {
            pluginInstance.logger.warning(err.message)
        }

    }

    private suspend fun updateMessage() {
        buildText()
        if (config.playersListMessageId.long == 0.toLong()) {
            sendNewMessageAndPin()
        } else {
            editMessage()
        }
    }

    suspend fun add(player: Player) {
        if (config.isPlayersListEnabled.boolean) {
            players.add(player)
            updateMessage()
        }
    }

    suspend fun remove(player: Player) {
        if (config.isPlayersListEnabled.boolean) {
            players.remove(player)
            updateMessage()
        }
    }

    fun update() {
        if (config.isPlayersListEnabled.boolean) {
            pluginInstance.launch {
                updateMessage()
            }
        }
    }

    fun clear() {
        if (config.isPlayersListEnabled.boolean) {
            players.clear()
            pluginInstance.launch {
                updateMessage()
            }
        }
    }
}
