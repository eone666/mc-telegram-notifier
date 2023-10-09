package io.github.eone666.telegramnotifier.commands

import io.github.eone666.telegramnotifier.pluginInstance
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.util.StringUtil
import java.util.Collections.sort


class Config : TabExecutor {
    private val config = pluginInstance.config

    private val configKeys = listOf(
        config.isNotificationsPlayerJoinEnabled.key,
        config.isNotificationsPlayerQuitEnabled.key,
        config.isNotificationsPrefixEnabled.key,
        config.notificationsPrefixText.key,
        config.isNotificationsSilentModeEnabled.key,
        config.isPlayerListEnabled.key,
        config.playerListMessageId.key,
        config.isPlayerListHeaderEnabled.key,
        config.isPlayerListFooterEnabled.key,
        config.playerListHeaderText.key,
        config.playerListFooterText.key,
    )

    private fun set(key: String, value: Any?, sender: CommandSender){
        config.set(key,value)
        sender.sendMessage("Property $key set to $value")
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) {
            return false
        }

        val value = args[1]

        when(val key = args.first()){
            config.isNotificationsPlayerJoinEnabled.key,
            config.isNotificationsPlayerQuitEnabled.key,
            config.isNotificationsPrefixEnabled.key,
            config.isNotificationsSilentModeEnabled.key -> {
                val bool: Boolean
                try {
                    bool = value.toBooleanStrict()
                } catch (_:IllegalArgumentException) {
                    sender.sendMessage("Argument should be true or false")
                    return true
                }
                set(key,bool,sender)
                return true
            }

            config.isPlayerListEnabled.key,
            config.isPlayerListHeaderEnabled.key,
            config.isPlayerListFooterEnabled.key -> {
                val bool: Boolean
                try {
                    bool = value.toBooleanStrict()
                } catch (_:IllegalArgumentException) {
                    sender.sendMessage("Argument should be true or false")
                    return true
                }
                set(key,bool,sender)
                pluginInstance.playerList.update()
                return true
            }

            config.playerListMessageId.key -> {
                val long = value.toLong()
                if(long != 0L){
                    sender.sendMessage("Argument can only be 0")
                    return true
                }
                pluginInstance.playerList.deleteMessage()
                set(key,long,sender)
                return true
            }

            config.notificationsPrefixText.key -> {
                val string = args.drop(1).joinToString(" ") + " "
                set(key,string,sender)
                return true
            }

            config.playerListHeaderText.key,
            config.playerListFooterText.key -> {
                var string = args.drop(1).joinToString(" ").replace("\\n", "\n")
                if(key == config.playerListHeaderText.key){
                    string += "\n"
                }
                set(key,string,sender)
                pluginInstance.playerList.update()
                return true
            }

            else -> {
                sender.sendMessage("Key is not supported")
                return true
            }
        }
    }
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): List<String> {
        if (args == null || args.size <= 1) {
            val completions: ArrayList<String> = ArrayList()
            StringUtil.copyPartialMatches<ArrayList<String>>(args?.get(0) ?: "", configKeys, completions)
            sort(completions)
            return completions
        }

        return when (args.first()) {
            config.isNotificationsPlayerJoinEnabled.key,
            config.isNotificationsPlayerQuitEnabled.key,
            config.isNotificationsPrefixEnabled.key,
            config.isNotificationsSilentModeEnabled.key,
            config.isPlayerListEnabled.key,
            config.isPlayerListHeaderEnabled.key,
            config.isPlayerListFooterEnabled.key -> {
                listOf("true", "false")
            }

            config.playerListMessageId.key -> listOf("0")

            else -> listOf()
        }

    }
}