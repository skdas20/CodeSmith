package com.codesmith.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeminiService {

    @Value("${gemini.api.key.primary}")
    private String primaryApiKey;

    @Value("${gemini.api.key.secondary}")
    private String secondaryApiKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();
    private boolean usePrimaryKey = true;

    public String generateContent(String prompt) throws Exception {
        String apiKey = usePrimaryKey ? primaryApiKey : secondaryApiKey;

        try {
            return callGeminiAPI(prompt, apiKey);
        } catch (Exception e) {
            if (e.getMessage().contains("429") ||
                e.getMessage().contains("limit") ||
                e.getMessage().contains("quota")) {
                usePrimaryKey = !usePrimaryKey;
                apiKey = usePrimaryKey ? primaryApiKey : secondaryApiKey;
                return callGeminiAPI(prompt, apiKey);
            }
            throw e;
        }
    }

    private String callGeminiAPI(String prompt, String apiKey) throws Exception {
        // Use gemini-2.0-flash-exp model with v1beta API
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-exp:generateContent?key=" + apiKey;

        JsonObject requestBody = new JsonObject();
        JsonArray contents = new JsonArray();
        JsonObject content = new JsonObject();
        JsonArray parts = new JsonArray();
        JsonObject part = new JsonObject();

        part.addProperty("text", prompt);
        parts.add(part);
        content.add("parts", parts);
        contents.add(content);
        requestBody.add("contents", contents);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestBody)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("API Error: " + response.statusCode() + " - " + response.body());
        }

        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
        return jsonResponse.getAsJsonArray("candidates")
                .get(0).getAsJsonObject()
                .getAsJsonObject("content")
                .getAsJsonArray("parts")
                .get(0).getAsJsonObject()
                .get("text").getAsString();
    }
}
