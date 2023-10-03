package io.github.eone666.telegramnotifier.commands.minecraft

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


class SendSilently : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (args.isNotEmpty()) {
            val value: Boolean
            try {
                value = args.first().toBooleanStrict()
            } catch (_:IllegalArgumentException) {
                sender.sendMessage("Argument should be true or false")
                return false
            }

            pluginInstance.config.isNotificationsSendSilently = value
            pluginInstance.config.save()

            sender.sendMessage("Silent mode ${if(value) "enabled" else "disabled"}")
            return true
        }

        return false
    }

}