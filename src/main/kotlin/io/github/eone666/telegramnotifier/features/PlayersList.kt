package io.github.eone666.telegramnotifier.features

import io.github.eone666.telegramnotifier.pluginInstance
import io.github.eone666.telegramnotifier.utils.telegram.ParseMode
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.json.simple.JSONObject
import java.util.stream.Collectors
import kotlin.toString

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

    private fun sendNewMessageAndPin() {
        val response = pluginInstance.tg.sendMessage(text,false, ParseMode.MARKDOWN)
        if(response != null){
            val isOk = response["ok"].toString().toBooleanStrict()
            if (isOk) {
                val resultObject = response["result"] as JSONObject
                val messageId = resultObject["message_id"].toString().toInt()
                pluginInstance.config.playersListMessageId = messageId
                pluginInstance.tg.pinChatMessage(messageId)
            }
        }
    }

    private fun editMessage() {
        val response = pluginInstance.tg.editMessageText(
            pluginInstance.config.playersListMessageId,
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
        if (pluginInstance.config.playersListMessageId == 0) {
            sendNewMessageAndPin()
        } else {
            editMessage()
        }
    }

    fun add(player: Player) {
        if (pluginInstance.config.isPlayersListEnabled) {
            players.add(player)
            updateMessage()
        }
    }

    fun remove(player: Player) {
        if (pluginInstance.config.isPlayersListEnabled) {
            players.remove(player)
            updateMessage()
        }
    }

    fun init() {
        if (pluginInstance.config.isPlayersListEnabled) {
            updateMessage()
        }
    }

    fun clear() {
        if (pluginInstance.config.isPlayersListEnabled) {
            players.clear()
            updateMessage()
        }
    }
}