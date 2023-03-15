package io.github.eone666.telegramnotifier.utils.telegram

import io.github.eone666.telegramnotifier.utils.Request
import org.bukkit.Bukkit
import org.json.simple.JSONObject
import kotlin.Any
import kotlin.Int
import kotlin.String
import kotlin.toString

class Telegram(token: String) {

    private val request: Request = Request(String.format("https://api.telegram.org/bot%s/", token))

    private fun handleErrors(response: JSONObject?) {
        if(response != null){
            val isOk = response["ok"].toString().toBooleanStrict()
            if (!isOk) {
                val errorCode = response["error_code"].toString().toInt()
                val description = response["description"].toString()
                Bukkit.getLogger().warning(String.format("Error code: %s", errorCode))
                Bukkit.getLogger().warning(String.format("Description: %s", description))
            }
        }
    }

    fun sendMessage(chatId: String, text: String, webPreview: Boolean, parseMode: ParseMode?): JSONObject? {
        val data: Map<String, Any> = object : HashMap<String, Any>() {
            init {
                put("chat_id", chatId)
                put("text", text)
                put("disable_web_page_preview", !webPreview)
                if(parseMode != null) {
                    put("parse_mode", parseMode.toString())
                }
            }
        }
        val response = request.postJson("sendMessage", data)
        handleErrors(response)
        return response
    }

    fun editMessageText(chatId: String, messageId: Int, text: String, webPreview: Boolean, parseMode: ParseMode?): JSONObject? {
        val data: Map<String, Any> = object : HashMap<String, Any>() {
            init {
                put("chat_id", chatId)
                put("message_id", messageId)
                put("text", text)
                put("disable_web_page_preview", !webPreview)
                if(parseMode != null) {
                    put("parse_mode", parseMode.toString())
                }
            }
        }
        val response = request.postJson("editMessageText", data)
        handleErrors(response)
        return response
    }

    fun pinChatMessage(chatId: String, messageId: Int): JSONObject? {
        val data: Map<String, Any> = object : HashMap<String, Any>() {
            init {
                put("chat_id", chatId)
                put("message_id", messageId)
            }
        }
        val response = request.postJson("pinChatMessage", data)
        handleErrors(response)
        return response
    }
}