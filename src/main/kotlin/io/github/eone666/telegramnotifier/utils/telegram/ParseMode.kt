package io.github.eone666.telegramnotifier.utils.telegram

enum class ParseMode(private val text: String) {
    MARKDOWN("Markdown"),
    HTML("HTML");

    override fun toString(): String {
        return text
    }
}