package io.github.eone666.telegramnotifier.commands.minecraft

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


class Setup : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        if(pluginInstance.config.isPluginConfigured){
            sender.sendMessage("Plugin is already configured")
            return true
        }

        if(args?.get(0) !== null){
            val token = args[0]
            pluginInstance.initBot(token)
            pluginInstance.setup.startSetup(token, sender)
            sender.sendMessage("Enter the \"/setup ${pluginInstance.setup.getCode()}\" command in the telegram bot")
            return true;
        }

        return false
    }

}