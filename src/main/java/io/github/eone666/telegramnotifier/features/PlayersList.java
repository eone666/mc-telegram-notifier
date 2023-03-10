package io.github.eone666.telegramnotifier.features;

import io.github.eone666.telegramnotifier.TelegramNotifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PlayersList {

    private TelegramNotifier plugin = null;
    private Collection<Player> players = new HashSet<Player>();
    String text = null;

    public PlayersList (TelegramNotifier pluginInstance) {
        plugin = pluginInstance;
    }

    private void buildText() {
        int playersCount = players.size();
        String playersTextByCount = playersCount == 0 ? "No players online" : "Players online:";
        String playerNames = players.stream().map(player -> player.getName()).map(Object::toString).collect(Collectors.joining("\n"));
        text = playersTextByCount + "\n" + playerNames;
        if(plugin.config.getIsPlayersListHeaderEnabled()){
            text = plugin.config.getPlayersListHeaderText() + "\n" + text;
        }
    }

    private void sendNewMessageAndPin () {
        JSONObject response = plugin.tg.sendMessage(plugin.config.getChatId(), text, "markdown");

        boolean isOk = Boolean.valueOf(response.get("ok").toString());
        if(isOk){
            JSONObject resultObject = (JSONObject) response.get("result");
            int messageId = Integer.parseInt(resultObject.get("message_id").toString());
            plugin.config.setPlayersListMessageId(messageId);
            plugin.tg.pinChatMessage(plugin.config.getChatId(), messageId);
        }
    }

    private void editMessage () {
        JSONObject response = plugin.tg.editMessageText(
                plugin.config.getChatId(),
                plugin.config.getPlayersListMessageId(),
                text, "markdown"
        );

        boolean isOk = Boolean.valueOf(response.get("ok").toString());
        if(!isOk){
            int errorCode = Integer.valueOf(response.get("error_code").toString());
            String description = response.get("description").toString();

            if (errorCode == 400 && description == "Bad Request: message to edit not found") {
                Bukkit.getLogger().info("Sending new message");
                sendNewMessageAndPin();
            }
        }
    }

    private void updateMessage () {
        buildText();
        if(plugin.config.getPlayersListMessageId() == 0) {
            sendNewMessageAndPin();
        } else {
            editMessage();
        }}

    public void join (Player player) {
        players.add(player);
        updateMessage();
    }

    public void quit (Player player) {
        players.remove(player);
        updateMessage();
    }

    public void init () {
        updateMessage();
    }

    public void disable () {
        players.clear();
        updateMessage();
    }

}
