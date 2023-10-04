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
        config.isPlayersListEnabled.key,
        config.playersListMessageId.key,
        config.isPlayersListHeaderEnabled.key,
        config.isPlayersListFooterEnabled.key,
    )

    private fun set(key: String, value: Any?, sender: CommandSender){
        config.set(key,value)
        config.save()
        sender.sendMessage("Property $key set to $value")
    }

    private fun setProperty(args: Array<out String>, sender: CommandSender):Boolean {
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
                    return false
                }
                set(key,bool,sender)
                return true
            }

            config.isPlayersListEnabled.key,
            config.isPlayersListHeaderEnabled.key,
            config.isPlayersListFooterEnabled.key -> {
                val bool: Boolean
                try {
                    bool = value.toBooleanStrict()
                } catch (_:IllegalArgumentException) {
                    sender.sendMessage("Argument should be true or false")
                    return false
                }
                set(key,bool,sender)
                if(bool){
                    sender.sendMessage("The header and footer text for the player list can only be set in the configuration file (because it multi-line). :( ")
                }
                pluginInstance.playersList.update()
                return true
            }

            config.playersListMessageId.key -> {
                val long = value.toLong()
                if(long != 0L){
                    sender.sendMessage("Argument can only be 0")
                    return false
                }
                set(key,long,sender)
                return true
            }

            config.notificationsPrefixText.key -> {
                set(key,value,sender)
                return true
            }

            else -> {
                sender.sendMessage("Key is not supported")
                return true
            }
        }
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

        return setProperty(args, sender)
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
            config.isPlayersListEnabled.key,
            config.isPlayersListHeaderEnabled.key,
            config.isPlayersListFooterEnabled.key -> {
                listOf("true", "false")
            }

            config.playersListMessageId.key -> listOf("0")

            else -> listOf()
        }

    }
}