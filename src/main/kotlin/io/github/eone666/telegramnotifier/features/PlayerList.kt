package io.github.eone666.telegramnotifier.features

import com.elbekd.bot.model.toChatId
import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.entity.Player
import java.util.stream.Collectors
import com.elbekd.bot.types.ParseMode.Markdown
import com.github.shynixn.mccoroutine.bukkit.launch

class PlayerList {
    private val config = pluginInstance.config
    private val tg = pluginInstance.bot?.tg

    private val players: MutableCollection<Player> = HashSet()
    private var text: String = ""
    private fun buildText() {
        val playersCount = players.size
        val playerNames = players.stream().map { obj: Player -> obj.name }.collect(Collectors.joining("\n"))

        text = if (playersCount == 0) "No players online" else "Players online:\n$playerNames"

        if (config.isPlayerListHeaderEnabled.get()) {
            text = "${config.playerListHeaderText.get()}\n$text"
        }

        if (config.isPlayerListFooterEnabled.get()) {
            text = "$text\n\n${config.playerListFooterText.get()}"
        }
    }

    private suspend fun sendNewMessageAndPin() {
        try {
            val response = tg?.sendMessage(
                chatId = config.chatId.get().toChatId(),
                disableNotification = config.isNotificationsSilentModeEnabled.get(),
                text = text,
                parseMode = Markdown,
                disableWebPagePreview = true
            )
            config.playerListMessageId.set(response!!.messageId)
            pluginInstance.logger.info("New message sent")
        } catch (err: Throwable) {
            pluginInstance.logger.warning(err.message)
        }

        try {
            tg?.pinChatMessage(
                chatId = config.chatId.get().toChatId(),
                disableNotification = config.isNotificationsSilentModeEnabled.get(),
                messageId = config.playerListMessageId.get()
            )
            pluginInstance.logger.info("Message pinned")
        } catch (err: Throwable) {
            pluginInstance.logger.warning(err.message)
        }
    }

    private suspend fun editMessage() {
        try {
            tg?.editMessageText(
                chatId = config.chatId.get().toChatId(),
                messageId = config.playerListMessageId.get(),
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
        if (config.playerListMessageId.get() == 0.toLong()) {
            sendNewMessageAndPin()
        } else {
            editMessage()
        }
    }

    suspend fun add(player: Player) {
        if (config.isPlayerListEnabled.get()) {
            players.add(player)
            updateMessage()
        }
    }

    suspend fun remove(player: Player) {
        if (config.isPlayerListEnabled.get()) {
            players.remove(player)
            updateMessage()
        }
    }

    fun update() {
        if (config.isPlayerListEnabled.get()) {
            pluginInstance.launch {
                updateMessage()
            }
        }
    }

    fun clear() {
        if (config.isPlayerListEnabled.get()) {
            players.clear()
            pluginInstance.launch {
                updateMessage()
            }
        }
    }
}
