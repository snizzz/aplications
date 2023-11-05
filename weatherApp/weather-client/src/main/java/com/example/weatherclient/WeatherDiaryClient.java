package com.example.weatherclient;

import com.example.weatherrestapi.model.ServiceResponse;
import com.example.weatherrestapi.model.diary.DiaryEntry;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

public class WeatherDiaryClient {
    private final WebClient client;


    public WeatherDiaryClient(WebClient client) {
        this.client = client;
    }


    public void addEntry(DiaryEntry newEntry) {
        client.post()
                .uri("http://localhost:8080/api/diary")
                .bodyValue(newEntry)
                .exchange()
                .subscribe();
    }

    public List<DiaryEntry> getAllEntries() {
        return Objects.requireNonNull(client.get()
                        .uri("http://localhost:8080/api/diary/all")
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ServiceResponse<List<DiaryEntry>>>() {
                        })
                        .block())
                .getData();
    }

    public void updateEntry(Integer id, DiaryEntry newEntry) {
        client.put()
                .uri("http://localhost:8080/api/diary/{id}", id)
                .bodyValue(newEntry)
                .exchange()
                .subscribe();
    }

    public DiaryEntry findEntryById(Integer id) {
        return Objects.requireNonNull(client.get()
                        .uri("http://localhost:8080/api/diary/{id}", id)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ServiceResponse<DiaryEntry>>() {
                        })
                        .block())
                .getData();
    }

    public DiaryEntry deleteEntry(Integer id) {
        return Objects.requireNonNull(client.delete()
                        .uri("http://localhost:8080/api/diary/{id}", id)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ServiceResponse<DiaryEntry>>() {
                        })
                        .block())
                .getData();
    }

}
