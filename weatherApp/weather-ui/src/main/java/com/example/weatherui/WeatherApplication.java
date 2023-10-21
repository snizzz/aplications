package com.example.weatherui;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherApplication {

    public static void main(String[] args) {
        Application.launch(WeatherUiApplication.class, args);
    }

}
