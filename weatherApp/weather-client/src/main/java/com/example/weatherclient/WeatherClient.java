package com.example.weatherclient;

import com.example.weatherclient.models.CityModel;
import com.example.weatherclient.models.WeatherModel;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;


public class WeatherClient {
    private final WebClient webClient;
    private final String apiKey = "Of3GRMJ6SaczIjfFVEHddAl4E3AiWSsQ";
    private final String language = "pl";
    private final String api_url = "http://dataservice.accuweather.com";
    private final String autocomplete = "locations/v1/cities/autocomplete?apikey=%s&q=%s&language=%s";
    private final String past6hoursConditions = "currentconditions/v1/%s/historical?apikey=%s&language=%s&details=true";
    private final String past24hoursConditions = "currentconditions/v1/%s/historical/24?apikey=%s&language=%s&details=true";
    private final String currentConditions = "currentconditions/v1/%s?apikey=%s&language=%s&details=true";
    private final String locationDetails = "locations/v1/%s?apikey=%s&language=%s&details=tru";

    public WeatherClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<CityModel> searchAutocomplete(String search) {
        String apiUrl = api_url + "/" + String.format(autocomplete, apiKey, search, language);
        Flux<CityModel> cityModelFlux = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(CityModel.class);

        return cityModelFlux.collectList().block();
    }


    public CityModel locationDetails(CityModel city) {
        String apiUrl = api_url + "/" + String.format(locationDetails, city.getKey(), apiKey, language);

        Flux<CityModel> cityModelFlux = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(CityModel.class);

        return Objects.requireNonNull(cityModelFlux.collectList().block()).get(0);
    }

    public WeatherModel currentConditions(CityModel city) {
        String apiUrl = api_url + "/" + String.format(currentConditions, city.getKey(), apiKey, language);

        Flux<WeatherModel> weatherModelFlux = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(WeatherModel.class);

        return Objects.requireNonNull(weatherModelFlux.collectList().block()).get(0);
    }

    public List<WeatherModel> conditionsPast6Hours(CityModel city) {
        String apiUrl = api_url + "/" + String.format(past6hoursConditions, city.getKey(), apiKey, language);
        Flux<WeatherModel> weatherModelFlux = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(WeatherModel.class);

        return weatherModelFlux.collectList().block();
    }

    public List<WeatherModel> conditionsPast24Hours(CityModel city) {
        String apiUrl = api_url + "/" + String.format(past24hoursConditions, city.getKey(), apiKey, language);
        Flux<WeatherModel> weatherModelFlux = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(WeatherModel.class);

        return weatherModelFlux.collectList().block();
    }
}
