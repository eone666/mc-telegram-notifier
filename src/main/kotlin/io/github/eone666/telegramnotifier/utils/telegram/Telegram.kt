package io.github.eone666.telegramnotifier.utils.telegram

import io.github.eone666.telegramnotifier.utils.Request
import org.bukkit.Bukkit
import org.json.simple.JSONObject
import kotlin.Int
import kotlin.String
import kotlin.toString

class Telegram(token: String, chatId: String, sendSilently: Boolean) {

    private val request: Request = Request(String.format("https://api.telegram.org/bot%s/", token))
    private val baseOptions: Map<String, String> = mapOf(
            "chat_id" to chatId,
            "disable_notification" to "$sendSilently"
    )

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

    fun sendMessage(text: String, webPreview: Boolean, parseMode: ParseMode?): JSONObject? {
        val data = baseOptions.toMutableMap()
        data["text"] = text
        data["disable_web_page_preview"] = (!webPreview).toString()
        if (parseMode != null) {
            data["parse_mode"] = "$parseMode"
        }

        val response = request.postJson("sendMessage", data)
        handleErrors(response)
        return response
    }

    fun editMessageText(messageId: Int, text: String, webPreview: Boolean, parseMode: ParseMode?): JSONObject? {
        val data = baseOptions.toMutableMap()
        data["message_id"] = "$messageId"
        data["text"] = text
        data["disable_web_page_preview"] = (!webPreview).toString()
        if (parseMode != null) {
            data["parse_mode"] = "$parseMode"
        }

        val response = request.postJson("editMessageText", data)
        handleErrors(response)
        return response
    }

    fun pinChatMessage(messageId: Int): JSONObject? {
        val data = baseOptions.toMutableMap()
        data["message_id"] = "$messageId"

        val response = request.postJson("pinChatMessage", data)
        handleErrors(response)
        return response
    }
}