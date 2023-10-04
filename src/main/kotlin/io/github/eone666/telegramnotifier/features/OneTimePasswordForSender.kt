package io.github.eone666.telegramnotifier.features

import org.bukkit.command.CommandSender
import kotlin.random.Random

val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

// One Time Password
class OneTimePasswordForSender(newToken: String, commandSender: CommandSender) {
    val code: String = (1..6)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")

    val token: String
    private val sender: CommandSender
    init {
        token = newToken
        sender = commandSender
    }

    fun sendMessage(message: String) {
        sender.sendMessage(message)
    }
}