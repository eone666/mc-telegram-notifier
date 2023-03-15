package io.github.eone666.telegramnotifier.features

import io.github.eone666.telegramnotifier.TelegramNotifier
import io.github.eone666.telegramnotifier.utils.telegram.ParseMode
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.json.simple.JSONObject
import java.util.stream.Collectors
import kotlin.String
import kotlin.toString

class PlayersList(private val plugin: TelegramNotifier) {
    private val players: MutableCollection<Player> = HashSet()
    private var text: String = ""
    private fun buildText() {
        val playersCount = players.size
        val playerNames = players.stream().map { obj: Player -> obj.name }.collect(Collectors.joining("\n"))

        text = if (playersCount == 0) "No players online" else "Players online:\n$playerNames"

        if (plugin.config.isPlayersListHeaderEnabled) {
            text = "${plugin.config.playersListHeaderText}\n$text"
        }

        if (plugin.config.isPlayersListFooterEnabled) {
            text = "$text\n\n${plugin.config.playersListFooterText}"
        }
    }

    private fun sendNewMessageAndPin() {
        val response = plugin.tg.sendMessage(plugin.config.chatId, text,false, ParseMode.MARKDOWN)
        if(response != null){
            val isOk = response["ok"].toString().toBooleanStrict()
            if (isOk) {
                val resultObject = response["result"] as JSONObject
                val messageId = resultObject["message_id"].toString().toInt()
                plugin.config.playersListMessageId = messageId
                plugin.tg.pinChatMessage(plugin.config.chatId, messageId)
            }
        }
    }

    private fun editMessage() {
        val response = plugin.tg.editMessageText(
            plugin.config.chatId,
            plugin.config.playersListMessageId,
            text,
            false,
            ParseMode.MARKDOWN
        )
        if(response != null){
            val isOk = response["ok"].toString().toBooleanStrict()
            if (!isOk) {
                val errorCode = response["error_code"].toString().toInt()
                val description = response["description"].toString()
                if (errorCode == 400 && description == "Bad Request: message to edit not found") {
                    Bukkit.getLogger().info("Sending new message")
                    sendNewMessageAndPin()
                }
            }
        }
    }

    private fun updateMessage() {
        buildText()
        if (plugin.config.playersListMessageId == 0) {
            sendNewMessageAndPin()
        } else {
            editMessage()
        }
    }

    fun add(player: Player) {
        if (plugin.config.isPlayersListEnabled) {
            players.add(player)
            updateMessage()
        }
    }

    fun remove(player: Player) {
        if (plugin.config.isPlayersListEnabled) {
            players.remove(player)
            updateMessage()
        }
    }

    fun init() {
        if (plugin.config.isPlayersListEnabled) {
            updateMessage()
        }
    }

    fun clear() {
        if (plugin.config.isPlayersListEnabled) {
            players.clear()
            updateMessage()
        }
    }
}