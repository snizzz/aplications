package com.example.weatherrestapi;

import com.example.weatherrestapi.utils.FakeDiaryEntryGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class WeatherRestApiApplication {


    @Bean
    public FakeDiaryEntryGenerator fakeDiaryEntryGenerator(){
        return new FakeDiaryEntryGenerator();
    }

    public static void main(String[] args) {
        SpringApplication.run(WeatherRestApiApplication.class, args);
    }

}
