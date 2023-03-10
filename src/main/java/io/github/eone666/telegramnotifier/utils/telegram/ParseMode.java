package io.github.eone666.telegramnotifier.utils.telegram;

public enum ParseMode {
    MARKDOWN("markdown"),
    HTML("html");
    private final String text;

    ParseMode(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
