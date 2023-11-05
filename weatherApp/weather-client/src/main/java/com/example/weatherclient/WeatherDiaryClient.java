package com.example.weatherclient;

import com.example.weatherrestapi.model.ServiceResponse;
import com.example.weatherrestapi.model.diary.DiaryEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WeatherDiaryClient {
    private final WebClient client;
    private final Map<String, String> apiEndpoints;

    public WeatherDiaryClient(WebClient client) {
        this.client = client;
        this.apiEndpoints = loadApiEndpoints();
    }


    private Map<String, String> loadApiEndpoints() {
        Map<String, String> endpoints = new HashMap<>();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("api_config.json");

            if (inputStream != null) {
                ObjectMapper mapper = new ObjectMapper();
                endpoints = mapper.readValue(inputStream, new TypeReference<Map<String, String>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return endpoints;
    }


    public void addEntry(DiaryEntry newEntry) {
        client.post()
                .uri(apiEndpoints.get("addEntry"))
                .bodyValue(newEntry)
                .exchange()
                .subscribe();
    }

    public List<DiaryEntry> getAllEntries() {
        return Objects.requireNonNull(client.get()
                        .uri(apiEndpoints.get("getAllEntries"))
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ServiceResponse<List<DiaryEntry>>>() {
                        })
                        .block())
                .getData();
    }

    public void updateEntry(Integer id, DiaryEntry newEntry) {
        client.put()
                .uri(apiEndpoints.get("updateEntry"), id)
                .bodyValue(newEntry)
                .exchange()
                .subscribe();
    }

    public DiaryEntry findEntryById(Integer id) {
        return Objects.requireNonNull(client.get()
                        .uri(apiEndpoints.get("findEntryById"), id)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ServiceResponse<DiaryEntry>>() {
                        })
                        .block())
                .getData();
    }

    public DiaryEntry deleteEntry(Integer id) {
        return Objects.requireNonNull(client.delete()
                        .uri(apiEndpoints.get("deleteEntry"), id)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ServiceResponse<DiaryEntry>>() {
                        })
                        .block())
                .getData();
    }
}
