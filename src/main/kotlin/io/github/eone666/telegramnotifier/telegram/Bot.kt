package io.github.eone666.telegramnotifier.telegram

import com.elbekd.bot.Bot
import com.elbekd.bot.model.toChatId
import io.github.eone666.telegramnotifier.pluginInstance
import kotlin.jvm.Throws

class Bot @Throws(IllegalArgumentException::class) constructor(token: String) {
    val tg: Bot

    init {
        this.tg = Bot.createPolling(token = token)
        this.tg.start()

        // register telegram commands
        this.tg.onCommand("/setup") { (msg, _) ->
            val args = msg.text?.split(" ")
            val chatId: Long = msg.chat.id;
            if (args?.get(1) == pluginInstance.oneTimePasswordForSender.code) {
                val token = pluginInstance.oneTimePasswordForSender.token
                pluginInstance.config.token = token // save token from minecraft command
                pluginInstance.config.chatId = chatId.toString() // save chat_id from person who wrote command to bot
                pluginInstance.config.isPluginConfigured = true
                pluginInstance.config.save()
                pluginInstance.initFeatures()
                this.tg.sendMessage(text = "Set up successfully", chatId = chatId.toChatId())
                pluginInstance.oneTimePasswordForSender.sendMessage("Set up successfully")
            } else {
                this.tg.sendMessage(text = "Wrong code", chatId = chatId.toChatId())
            }
        }
    }

    fun stop() {
        this.tg.stop()
    }
}