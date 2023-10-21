package com.example.weatherui;

import com.example.weatherclient.WeatherClient;
import com.example.weatherclient.models.CityModel;
import com.example.weatherclient.models.WeatherModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
@Import(com.example.weatherclient.ClientConfiguration.class)
public class WeatherUiController{
    @FXML
    public LineChart<String, Double> chart;
    @FXML
    public ListView<String> searchResults;
    @FXML
    public Button searchButton;
    @FXML
    public TextField searchText;

    private final WeatherClient weatherClient;

    public WeatherUiController(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public void search(MouseEvent mouseEvent) {
        String search = searchText.getText();
        List<CityModel> searchResult = weatherClient.searchAutocomplete(search);
        for (CityModel city : searchResult) {
            searchResults.getItems().add(city.toString());
        }
    }
}
