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
        config.playersListHeaderText.key,
        config.isPlayersListFooterEnabled.key,
        config.playersListFooterText.key,
    )

    private fun setProperty(key: String, value: String, sender: CommandSender):Boolean {
        when(key){
            config.isNotificationsPlayerJoinEnabled.key,
            config.isNotificationsPlayerQuitEnabled.key,
            config.isNotificationsPrefixEnabled.key,
            config.isNotificationsSilentModeEnabled.key,
            config.isPlayersListEnabled.key,
            config.isPlayersListHeaderEnabled.key,
            config.isPlayersListFooterEnabled.key -> {
                val bool: Boolean;
                try {
                    bool = value.toBooleanStrict()
                } catch (_:IllegalArgumentException) {
                    sender.sendMessage("Argument should be true or false")
                    return false
                }
                config.set(key,bool)
                sender.sendMessage("Property $key set to $value")
                return true
            }

            config.playersListMessageId.key -> {
                val long = value.toLong()
                if(long != 0L){
                    sender.sendMessage("Argument can only be 0")
                    return false
                }
                config.set(key,long)
                sender.sendMessage("Property $key set to $value")
                return true
            }

            else -> {
                if(!configKeys.contains(key)){
                    sender.sendMessage("Key is not supported")
                } else {
                    config.set(key, value)
                    sender.sendMessage("Property $key set to $value")
                }
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

        return setProperty(args.first(), args[1], sender)
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