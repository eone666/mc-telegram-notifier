package io.github.eone666.telegramnotifier.commands.minecraft

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


class CancelSetup : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(pluginInstance.config.isPluginConfigured){
            sender.sendMessage("Plugin is already configured")
            return true
        }

        if(pluginInstance.bot == null){
            sender.sendMessage("Setup is not running")
            return true
        }

        pluginInstance.bot!!.stop()
        sender.sendMessage("Setup is cancelled")

        return true
    }

}