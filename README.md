# Telegram Notifier

[![Modrinth Downloads](https://img.shields.io/modrinth/dt/5MPMN9NC?style=flat-square&logo=Modrinth)](https://modrinth.com/plugin/telegram-notifier)
[![Modrinth Followers](https://img.shields.io/modrinth/followers/5MPMN9NC?style=flat-square&logo=Modrinth)](https://modrinth.com/plugin/telegram-notifier)

This plugin provides a way to receive notifications on a Telegram chat when a player joins or quits your Minecraft server.

## Installation

1. Download the latest version from the [releases page](https://github.com/eone666/telegram-notifier/releases).
2. Place the downloaded JAR file into your server's `plugins` folder.
3. Start the server.

## Configuration

1. After starting the server with the plugin installed, a `config.yml` file will be generated in the `plugins/TelegramNotifier` folder.
2. Open the `config.yml` file and set the `enabled` option to `true`.
3. Obtain a Telegram Bot API token and chat ID. Follow [these instructions](https://core.telegram.org/bots#how-do-i-create-a-bot) to create a bot and obtain the token, and [these instructions](https://stackoverflow.com/a/32572159) to obtain the chat ID.
4. Set the `token` and `chatId` options in the `config.yml` file to the values obtained in step 3.
5. Set the `prefix` option to a string that will be used as a prefix in the messages sent to the Telegram chat (optional).

## Usage

After configuring the plugin, you will receive a notification in the configured Telegram chat when a player joins or quits the server.

![screenshot](https://github.com/eone666/telegram-notifier/raw/main/images/screenshot.png)

## Roadmap

- Add support for custom messages: Currently, the plugin sends a default message when a player joins or quits the server. Add support for custom messages that can be configured in the plugin's configuration file.
- Add support for localization: Currently, the plugin only supports a single language. Consider adding support for localization, so that the plugin can be used in different regions and languages. This could be useful for servers with international player bases.
- Add commands that help to set up the plugin without editing config manually.

## Used On

This plugin is currently being used on the following Minecraft server:

- Server IP: `193.123.62.49:25565`
- Telegram chat: [eone666_papermc](https://t.me/eone666_papermc)

## Contributing

Contributions to this project are welcome! If you find a bug or have a feature request, please create an issue on the [GitHub repository](https://github.com/eone666/telegram-notifier/issues). If you would like to contribute code, please fork the repository and create a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/eone666/telegram-notifier/blob/main/LICENSE) file for details.
