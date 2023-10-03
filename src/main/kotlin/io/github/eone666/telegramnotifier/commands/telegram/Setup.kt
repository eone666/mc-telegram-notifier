package io.github.eone666.telegramnotifier.commands.telegram

import com.elbekd.bot.model.toChatId
import io.github.eone666.telegramnotifier.pluginInstance

class Setup {

    fun init () {
        pluginInstance.tg?.onCommand("/setup"){(msg,_)->
            val args = msg.text?.split(" ")
            if(pluginInstance.setup.compareCode(args?.get(1))){
                val token = pluginInstance.setup.getToken()
                if(token !== null) {
                    pluginInstance.config.token = token
                    pluginInstance.config.chatId = msg.chat.id.toString()
                    pluginInstance.config.isPluginConfigured = true
                    pluginInstance.config.save()
                    pluginInstance.setup.clearAll()
                    pluginInstance.initFeatures()
                    pluginInstance.tg?.sendMessage(text = "Set up successfully", chatId = msg.chat.id.toChatId())
                    pluginInstance.setup.sendMessage("Set up successfully")
                }
            } else {
                pluginInstance.tg?.sendMessage(text = "Wrong code", chatId = msg.chat.id.toChatId())
            }
        }
    }

}