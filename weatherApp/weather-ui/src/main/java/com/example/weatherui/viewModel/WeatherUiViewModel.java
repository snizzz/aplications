package com.example.weatherui.viewModel;

import com.example.weatherclient.WeatherClient;
import com.example.weatherclient.WeatherDiaryClient;
import com.example.weatherclient.models.CityModel;
import com.example.weatherclient.models.WeatherModel;
import com.example.weatherui.view.WeatherDiaryUiController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WeatherUiViewModel {
    private BooleanProperty currentConditionsButton;
    private BooleanProperty resetSelectedButton;
    private BooleanProperty conditionsPast6HoursButton;
    private BooleanProperty conditionsPast24HoursButton;
    private BooleanProperty locationDetailsButton;
    private BooleanProperty currentConditionsAsyncButton;
    private BooleanProperty currentConditionsAsync1Button;
    private BooleanProperty currentConditionsAsync2Button;
    private ObjectProperty<TreeItem<String>> resultFieldRoot;
    private StringProperty loggerField;
    private StringProperty searchText;
    private final WeatherClient weatherClient;
    private final WeatherDiaryClient weatherDiaryClient;
    private String messages= "";
    @Getter
    private ObservableList<CityModel> selectedLocations;
    @Getter
    private ObservableList<CityModel> searchResults;

    public BooleanProperty currentConditionsButtonProperty() {
        return currentConditionsButton;
    }
    public BooleanProperty resetSelectedButtonProperty() {
        return resetSelectedButton;
    }
    public BooleanProperty conditionsPast6HoursButtonProperty() {
        return conditionsPast6HoursButton;
    }
    public BooleanProperty conditionsPast24HoursButtonProperty() {
        return conditionsPast24HoursButton;
    }
    public BooleanProperty locationDetailsButtonProperty() {
        return locationDetailsButton;
    }
    public BooleanProperty currentConditionsAsyncButtonProperty() {
        return currentConditionsAsyncButton;
    }
    public BooleanProperty currentConditionsAsync1ButtonProperty() {
        return currentConditionsAsync1Button;
    }
    public BooleanProperty currentConditionsAsync2ButtonProperty() {
        return currentConditionsAsync2Button;
    }
    public ObjectProperty<TreeItem<String>> resultFieldRootProperty() {
        return resultFieldRoot;
    }
    public StringProperty loggerFieldProperty() {
        return loggerField;
    }
    public StringProperty searchTextProperty() {
        return searchText;
    }

    public WeatherUiViewModel(WeatherClient weatherClient, WeatherDiaryClient weatherDiaryClient) {
        this.weatherClient = weatherClient;
        this.weatherDiaryClient = weatherDiaryClient;
        this.currentConditionsButton = new SimpleBooleanProperty(true);
        this.resetSelectedButton = new SimpleBooleanProperty(true);
        this.conditionsPast6HoursButton = new SimpleBooleanProperty(true);
        this.conditionsPast24HoursButton = new SimpleBooleanProperty(true);
        this.locationDetailsButton = new SimpleBooleanProperty(true);
        this.currentConditionsAsyncButton = new SimpleBooleanProperty(true);
        this.currentConditionsAsync1Button = new SimpleBooleanProperty(true);
        this.currentConditionsAsync2Button = new SimpleBooleanProperty(true);
        this.resultFieldRoot =  new SimpleObjectProperty<>(new TreeItem<>("Root"));
        this.loggerField = new SimpleStringProperty();
        this.searchText = new SimpleStringProperty();

        selectedLocations = FXCollections.observableArrayList();
        searchResults = FXCollections.observableArrayList();
    }

    private void enableButtons() {
        currentConditionsButton.set(false);
        resetSelectedButton.set(false);
        conditionsPast6HoursButton.set(false);
        conditionsPast24HoursButton.set(false);
        locationDetailsButton.set(false);
        currentConditionsAsyncButton.set(false);
        currentConditionsAsync1Button.set(false);
        currentConditionsAsync2Button.set(false);
    }
    private void disableButtons() {
        currentConditionsButton.set(true);
        resetSelectedButton.set(true);
        conditionsPast6HoursButton.set(true);
        conditionsPast24HoursButton.set(true);
        locationDetailsButton.set(true);
        currentConditionsAsyncButton.set(true);
        currentConditionsAsync1Button.set(true);
        currentConditionsAsync2Button.set(true);
    }
    private TreeItem<String> prepareWeatherTreeView(WeatherModel weather, String locationName) {
        TreeItem<String> root = new TreeItem<>(locationName);

        TreeItem<String> temperature = new TreeItem<>("Temperatura");
        TreeItem<String> imperial = new TreeItem<>("Jednostki imperialne: " + weather.getTemperature().getImperial().getValue() + weather.getTemperature().getImperial().getUnit());
        TreeItem<String> metric = new TreeItem<>("System metryczny: " + weather.getTemperature().getMetric().getValue() + weather.getTemperature().getMetric().getUnit());
        temperature.getChildren().addAll(imperial, metric);

        TreeItem<String> humidity = new TreeItem<>("Wilgotność: " + weather.getRelativeHumidity());

        TreeItem<String> wind = new TreeItem<>("Wiatr");
        TreeItem<String> speed = new TreeItem<>("Prędkość: "+weather.getWind().getSpeed().getMetric().getValue()+"km/h");
        TreeItem<String> direction = new TreeItem<>("Kierunek: "+weather.getWind().getDirection().toString());
        wind.getChildren().addAll(speed, direction);

        TreeItem<String> pressure = new TreeItem<>("Ciśnienie");
        TreeItem<String> imperialP = new TreeItem<>("Jednostki imperialne: " + weather.getPressure().getImperial().getValue() + weather.getPressure().getImperial().getUnit());
        TreeItem<String> metricP = new TreeItem<>("System metryczny: " + weather.getPressure().getMetric().getValue() + weather.getPressure().getMetric().getUnit());
        pressure.getChildren().addAll(imperialP, metricP);

        TreeItem<String> description = new TreeItem<>("Opis");
        description.getChildren().add(new TreeItem<>(weather.getWeatherText()));

        root.getChildren().addAll(temperature,wind,pressure,description);

        return root;
    }
    private TreeItem<String> prepareLocationDetailsTreeView(CityModel location) {
        TreeItem<String> root = new TreeItem<>(location.toString());

        TreeItem<String> country = new TreeItem<>("Państwo: "+location.getCountry().getLocalizedName());
        TreeItem<String> adminArea = new TreeItem<>("Jednostka administracyjna: "+location.getAdministrativeArea().getLocalizedName());
        TreeItem<String> type = new TreeItem<>("Typ: "+location.getType());
        TreeItem<String> population = new TreeItem<>("Populacja: "+location.getDetails().getPopulation());

        float latitude = location.getGeoPosition().getLatitude();
        float longitude = location.getGeoPosition().getLongitude();
        String sLatitude = latitude<0?latitude*(-1.0)+"S":latitude+"N";
        String sLongitude = longitude<0?longitude*(-1.0)+"W":longitude+"E";

        TreeItem<String> geoPosition = new TreeItem<>("Współrzędne geograficzne: " + sLatitude+" "+sLongitude );
        TreeItem<String> elevation = new TreeItem<>("Wysokość n.p.m: " + location.getGeoPosition().getElevation().getMetric().getValue());
        TreeItem<String> timeZone = new TreeItem<>("Strefa czasowa: " + location.getTimeZone().getName());

        root.getChildren().addAll(country,adminArea,type, population, geoPosition,elevation,timeZone);

        return root;
    }
    private void displayPastConditions(CityModel city, List<WeatherModel> pastConditions) {
        TreeItem<String> location = new TreeItem<>(city.toString());
        double minTemp = Double.POSITIVE_INFINITY;
        double maxTemp = Double.NEGATIVE_INFINITY;
        for(WeatherModel condition: pastConditions){
            if(condition.getTemperature().getMetric().getValue()<minTemp){
                minTemp = condition.getTemperature().getMetric().getValue();
            }
            if(condition.getTemperature().getMetric().getValue()>maxTemp){
                maxTemp = condition.getTemperature().getMetric().getValue();
            }


            long epochTimeMillis = condition.getEpochTime() * 1000;
            Instant instant = Instant.ofEpochMilli(epochTimeMillis);
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = localDateTime.format(formatter);

            TreeItem<String> time = new TreeItem<>(formattedDateTime);

            time.getChildren().addAll(prepareWeatherTreeView(condition,"").getChildren());
            location.getChildren().add(time);
        }
        TreeItem<String> minTempItem = new TreeItem<>("Minimalna temperatura w stopniach Celsjusza:  "+minTemp);
        TreeItem<String> maxTempItem = new TreeItem<>("Maksymalna temperatura w stopniach Celsjusza: "+maxTemp);
        location.getChildren().addAll(0, new ArrayList<>(Arrays.asList(minTempItem, maxTempItem)));
        resultFieldRoot.getValue().getChildren().add(location);
    }
    private void addToLog(String s){
        messages = messages + "\n" + s + "\n";
        loggerField.set(messages);
    }

    public void resetSelected(){
        selectedLocations.clear();
        resultFieldRoot.setValue(new TreeItem<>("Root"));
        disableButtons();
    }
    public void search() {
        searchResults.clear();
        String search = searchText.getValue();
        addToLog("Wyszukiwanie: "+search);
        List<CityModel> searchResult = weatherClient.searchAutocomplete(search);
        searchResults.addAll(searchResult);
        addToLog("Zakończono wyszukiwanie: "+search);
    }
    public void searchResultClicked(CityModel selectedItem) {
        selectedLocations.add(selectedItem);
        enableButtons();
    }

    public void conditionsPast6Hours() {
        resultFieldRoot.setValue(new TreeItem<>("Root"));
        for (CityModel city : selectedLocations) {
            addToLog("Przygotowywanie warunków pogodowych z ostatnich 6h dla "+city.toString() + " ...");
            List<WeatherModel> conditionsPast6Hours = weatherClient.conditionsPast6Hours(city);
            addToLog("Zakończono pobieranie danych");
            addToLog("Wyświetlanie ...");
            displayPastConditions(city, conditionsPast6Hours);
            addToLog("Zakończono przygotowywanie");
        }
    }
    public void conditionsPast24Hours() {
        resultFieldRoot.setValue(new TreeItem<>("Root"));
        for (CityModel city : selectedLocations) {
            addToLog("Przygotowywanie warunków pogodowych z ostatnich 24h dla "+city.toString() + " ...");
            List<WeatherModel> conditionsPast24Hours = weatherClient.conditionsPast24Hours(city);
            addToLog("Zakończono pobieranie danych");
            addToLog("Wyświetlanie ...");
            displayPastConditions(city, conditionsPast24Hours);
            addToLog("Zakończono przygotowywanie");
        }
    }

    public void locationDetails() {
        resultFieldRoot.setValue(new TreeItem<>("Root"));
        for (CityModel city : selectedLocations) {
            CityModel locationDetailed = weatherClient.locationDetails(city);

            TreeItem<String> item = prepareLocationDetailsTreeView(locationDetailed);
            resultFieldRoot.getValue().getChildren().add(item);
        }
    }

    public void currentConditions() {
        resultFieldRoot.setValue(new TreeItem<>("Root"));
        for (CityModel city : selectedLocations) {
            addToLog("Przygotowywanie warunków dla "+city.toString() + " ...");
            WeatherModel weather = weatherClient.currentConditions(city);
            addToLog("Zakończono pobieranie danych");
            addToLog("Wyświetlanie ...");
            TreeItem<String> item = prepareWeatherTreeView(weather, city.toString());
            resultFieldRoot.getValue().getChildren().add(item);
            addToLog("Zakończono przygotowywanie");
        }
    }

    public void currentConditionsAsync() {
        resultFieldRoot.setValue(new TreeItem<>("Root"));
        for (CityModel city : selectedLocations) {
            addToLog("Przygotowywanie warunków dla "+city.toString() + " ...");
            CompletableFuture.runAsync(() -> {
                WeatherModel weather = weatherClient.currentConditions(city);
                TreeItem<String> item = prepareWeatherTreeView(weather, city.toString());
                resultFieldRoot.getValue().getChildren().add(item);
                addToLog("Zakończono przygotowywanie warunków dla "+ city);
            });
        }
    }

    public void currentConditionsAsync1() {
        resultFieldRoot.setValue(new TreeItem<>("Root"));

        List<CompletableFuture<WeatherModel>> temperatureFutures = new ArrayList<>();

        for (CityModel city : selectedLocations) {
            addToLog("Przygotowywanie warunków dla "+city.toString() + " ...");
            CompletableFuture<WeatherModel> temperatureFuture = CompletableFuture.supplyAsync(() -> {
                WeatherModel weather =  weatherClient.currentConditions(city);
                addToLog("Zakończono przygotowywanie warunków dla "+ city);
                return weather;
            });
            temperatureFutures.add(temperatureFuture);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(temperatureFutures.toArray(new CompletableFuture[0]));

        allOf.thenRun(() -> {
            for (CompletableFuture<WeatherModel> temperatureFuture : temperatureFutures) {
                TreeItem<String> item = prepareWeatherTreeView(temperatureFuture.join(), "...");
                resultFieldRoot.getValue().getChildren().add(item);
            }
        });
    }

    public void currentConditionsAsync2() {
        resultFieldRoot.setValue(new TreeItem<>("Root"));

        List<CompletableFuture<Pair<CityModel,WeatherModel>>> temperatureFutures = new ArrayList<>();

        for (CityModel city : selectedLocations) {
            addToLog("Przygotowywanie warunków dla "+city.toString() + " ...");
            CompletableFuture<Pair<CityModel,WeatherModel>> temperatureFuture = CompletableFuture.supplyAsync(() -> {
                Pair<CityModel,WeatherModel> res = new Pair<>(city, weatherClient.currentConditions(city));
                addToLog("Zakończono przygotowywanie warunków dla "+city.toString());
                return res;
            });

            temperatureFutures.add(temperatureFuture);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(temperatureFutures.toArray(new CompletableFuture[0]));

        allOf.thenRun(() -> {
            for (CompletableFuture<Pair<CityModel,WeatherModel>> temperatureFuture : temperatureFutures) {
                TreeItem<String> item = prepareWeatherTreeView(temperatureFuture.join().getValue(), temperatureFuture.join().getKey().toString());
                resultFieldRoot.getValue().getChildren().add(item);
            }
        });
    }

    public void openDiary() {


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WeatherDiary.fxml"));
            Parent parent = fxmlLoader.load();
            WeatherDiaryUiController ctrl = fxmlLoader.getController();

            ctrl.init(new WeatherDiaryUiViewModel(weatherDiaryClient));

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Weather diary app");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
