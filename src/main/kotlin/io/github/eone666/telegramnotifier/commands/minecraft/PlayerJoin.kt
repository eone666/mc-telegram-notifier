package io.github.eone666.telegramnotifier.commands.minecraft

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


class PlayerJoin : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        if(args?.get(0) !== null){
            val value: Boolean
            try {
                value = args[0].toBooleanStrict()
            } catch (_:Throwable) {
                sender.sendMessage("Argument should be true or false")
                return false
            }

            pluginInstance.config.isNotificationsPlayerJoinEnabled = value
            pluginInstance.config.save()

            sender.sendMessage("Player join notification ${if(value) "enabled" else "disabled"}")
            return true
        }

        return false
    }

}