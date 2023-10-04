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

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) {
            return false
        }

        when(args.first()) {
            config.isNotificationsPlayerJoinEnabled.key -> {
                return true
            }
            config.isNotificationsPlayerQuitEnabled.key -> {
                return true
            }
            config.isNotificationsPrefixEnabled.key -> {
                return true
            }
            config.notificationsPrefixText.key -> {
                return true
            }
            config.isNotificationsSilentModeEnabled.key -> {
                return true
            }
            config.isPlayersListEnabled.key -> {
                return true
            }
            config.playersListMessageId.key -> {
                return true
            }
            config.isPlayersListHeaderEnabled.key -> {
                return true
            }
            config.playersListHeaderText.key -> {
                return true
            }
            config.isPlayersListFooterEnabled.key -> {
                return true
            }
            config.playersListFooterText.key -> {
                return true
            }
            else -> {
                sender.sendMessage("Key is not supported")
                return true
            }
        }
    }

}