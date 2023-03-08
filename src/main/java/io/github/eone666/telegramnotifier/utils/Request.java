package io.github.eone666.telegramnotifier.utils;

import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.simple.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class Request {

    private String _baseUrl = null;

    public Request(String baseUrl) {
        _baseUrl = baseUrl;
    }
    @Nullable
    public HttpResponse<String> postJson (String url, HashMap<String, String> data) {

        HttpResponse<String> response = null;

        try {

            var client = HttpClient.newHttpClient();

            var jsonObject = new JSONObject(data);

            var request = HttpRequest.newBuilder()
                    .uri(URI.create(_baseUrl + url))
                    .setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toJSONString()))
                    .build();

            Bukkit.getLogger().info(jsonObject.toJSONString());

            CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            response = futureResponse.get();

            Bukkit.getLogger().info(response.body().toString());


        } catch (Throwable err) {
            Bukkit.getLogger().info(err.getMessage());
        }

        return response;
    }

}
