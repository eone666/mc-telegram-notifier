package io.github.eone666.telegramnotifier.utils

import org.bukkit.Bukkit
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class Request(private val _baseUrl: String) {
    private val jsonParser = JSONParser()
    fun postJson(url: String, data: Map<String?, Any?>?): JSONObject? {
        var jsonResponse: JSONObject? = null
        try {
            val client = HttpClient.newHttpClient()
            val jsonObject = JSONObject(data)
            val request = HttpRequest.newBuilder()
                .uri(URI.create(_baseUrl + url))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toJSONString()))
                .build()
            val futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            val response = futureResponse.get()
            try {
                jsonResponse = jsonParser.parse(response.body()) as JSONObject
            } catch (err: Throwable) {
                Bukkit.getLogger().warning(err.message)
            }
        } catch (err: Throwable) {
            Bukkit.getLogger().warning(err.message)
        }
        return jsonResponse
    }
}