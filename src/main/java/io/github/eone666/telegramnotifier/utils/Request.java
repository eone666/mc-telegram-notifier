package io.github.eone666.telegramnotifier.utils;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Request  {

    private final String _baseUrl;

    public Request(String baseUrl) {
        _baseUrl = baseUrl;
    }

    private final JSONParser jsonParser = new JSONParser();

    public JSONObject postJson (String url, Map<String, Object> data) {

        JSONObject jsonResponse = null;

        try {

            var client = HttpClient.newHttpClient();

            var jsonObject = new JSONObject(data);

            var request = HttpRequest.newBuilder()
                    .uri(URI.create(_baseUrl + url))
                    .setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toJSONString()))
                    .build();

            CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> response = futureResponse.get();

            try {
                jsonResponse = (JSONObject) jsonParser.parse(response.body());
            } catch (Throwable err) {
                Bukkit.getLogger().warning(err.getMessage());
            }

        } catch (Throwable err) {
            Bukkit.getLogger().warning(err.getMessage());
        }


        return jsonResponse;
    }

}
