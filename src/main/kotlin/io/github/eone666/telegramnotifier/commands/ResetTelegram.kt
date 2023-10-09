package io.github.eone666.telegramnotifier.commands

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


class ResetTelegram : CommandExecutor {
    val config = pluginInstance.config
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if(!config.isPluginConfigured.get()){
            sender.sendMessage("Telegram is not configured")
            return true
        }

        pluginInstance.playerList.deleteMessage()
        config.playerListMessageId.set(config.playerListMessageId.defaultValue)
        config.isPluginConfigured.set(false)
        config.token.set(config.token.defaultValue)
        config.chatId.set(config.chatId.defaultValue)
        pluginInstance.bot?.stop()
        pluginInstance.bot = null
        sender.sendMessage("Telegram configuration successfully reset")
        return true
    }

}