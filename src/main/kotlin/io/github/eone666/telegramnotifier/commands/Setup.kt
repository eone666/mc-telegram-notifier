package io.github.eone666.telegramnotifier.commands

import io.github.eone666.telegramnotifier.pluginInstance
import io.github.eone666.telegramnotifier.telegram.Bot
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

import io.github.eone666.telegramnotifier.features.OneTimePasswordForSender


class Setup : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (pluginInstance.config.isPluginConfigured.get()){
            sender.sendMessage("Plugin is already configured")
            return true
        }

        if (args.isNotEmpty()) {
            val token = args.first()
            pluginInstance.bot = Bot(token)
            pluginInstance.oneTimePasswordForSender = OneTimePasswordForSender(token, sender)
            val code: String = pluginInstance.oneTimePasswordForSender.code
            sender.sendMessage("Enter the \"/setup ${code}\" command in the telegram bot")
            return true
        }

        return false
    }

}