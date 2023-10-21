package com.example.weatherclient;

import com.example.weatherclient.models.CityModel;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;


public class WeatherClient {
    private final WebClient webClient;
    private final String apiKey = "";
    private final String language = "pl";
    private final String api_url = "http://dataservice.accuweather.com";
    private final String autocomplete_endpoint = "locations/v1/cities/autocomplete?apikey=%s&q=%s&language=%s";
    public WeatherClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<String> callExternalApi() {
        String apiUrl = "https://api.example.com/data"; // Replace with the API URL you want to call
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(String.class);
    }

    public List<CityModel> searchAutocomplete(String search) {
        String apiUrl = api_url + "/" + String.format(autocomplete_endpoint, apiKey, search, language);
        Flux<CityModel> cityModelFlux = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(CityModel.class);

        return cityModelFlux.collectList().block();
    }


}
