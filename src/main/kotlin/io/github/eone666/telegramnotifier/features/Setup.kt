package io.github.eone666.telegramnotifier.features

import org.bukkit.command.CommandSender
import kotlin.random.Random

val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

// One Time Password
class Setup {

    private var code: String? = null
    private var token: String? = null
    private var sender: CommandSender? = null

    fun startSetup (newToken: String, commandSender: CommandSender) {
        code = (1..6)
            .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
            .joinToString("")
        token = newToken
        sender = commandSender
    }

    fun getCode (): String? {
        return code
    }

    fun compareCode (optToCompare: String?):Boolean {
        return optToCompare == code
    }

    fun clearAll () {
        code = null
        token = null
    }

    fun getToken(): String? {
        return token
    }

    fun sendMessage(message: String) {
        sender?.sendMessage(message)
    }

}