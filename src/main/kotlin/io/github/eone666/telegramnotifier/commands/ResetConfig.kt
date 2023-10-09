package io.github.eone666.telegramnotifier.commands

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


class ResetConfig : CommandExecutor {
    val config = pluginInstance.config
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if(config.isPluginConfigured.get()){
            pluginInstance.playerList.deleteMessage()
        }
        config.reset()
        pluginInstance.bot?.stop()
        pluginInstance.bot = null
        sender.sendMessage("Configuration successfully reset")
        return true
    }

}