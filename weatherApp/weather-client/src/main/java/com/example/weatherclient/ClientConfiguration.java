package com.example.weatherclient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public WeatherClient weatherClient(WebClient webClient){
        return new WeatherClient(webClient);
    }
    @Bean
    public WeatherDiaryClient weatherDiaryClient(WebClient webClient){
        return new WeatherDiaryClient(webClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public WebClient webClient(){
        return WebClient.builder().build();
    }

}
