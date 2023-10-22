package com.example.weatherui;

import com.example.weatherclient.WeatherClient;
import com.example.weatherclient.models.CityModel;
import com.example.weatherclient.models.WeatherModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
@Import(com.example.weatherclient.ClientConfiguration.class)
public class WeatherUiController implements Initializable {
    @FXML
    public LineChart<String, Double> chart;
    @FXML
    public ListView<CityModel> searchResults;
    @FXML
    public Button searchButton;
    @FXML
    public TextField searchText;

    private final WeatherClient weatherClient;
    @FXML
    public ListView<CityModel> selectedLocations;
    @FXML
    public Label optionsLabel;
    @FXML
    public Label selectedLabel;
    @FXML
    public Label searchLabel;
    @FXML
    public Button currentConditionsButton;
    @FXML
    public Button resetSelectedButton;
    @FXML
    public Button conditionsPast6HoursButton;
    @FXML
    public Button conditionsPast24HoursButton;
    @FXML
    public Button locationDetailsButton;
    @FXML
    public Button currentConditionsAsyncButton;
    @FXML
    public Button currentConditionsAsync1Button;
    @FXML
    public Button currentConditionsAsync2Button;
    @FXML
    public Label resultFieldLabel;
    @FXML
    public Button restetSelectedButton;
    @FXML
    public TreeView<String> resultField;
    public Label resultFieldLabel1;
    @FXML
    public TextArea loggerField;
    private String messages= "";

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

    private void addToLog(String s){
        messages = messages + "\n" + s + "\n";
        loggerField.setText(messages);
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
        TreeItem<String> elevation = new TreeItem<>("Wysokość n.p.m: " + location.getGeoPosition().getElevation());
        TreeItem<String> timeZone = new TreeItem<>("Strefa czasowa: " + location.getTimeZone().getName());

        root.getChildren().addAll(country,adminArea,type, population, geoPosition,elevation,timeZone);

        return root;
    }

    public WeatherUiController(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public void search(MouseEvent mouseEvent) {
        searchResults.getItems().clear();
        String search = searchText.getText();
        addToLog("Wyszukiwanie: "+search);
        List<CityModel> searchResult = weatherClient.searchAutocomplete(search);
        for (CityModel city : searchResult) {
            searchResults.getItems().add(city);
        }
        addToLog("Zakończono wyszukiwanie: "+search);
    }

    public void resetSelected(MouseEvent mouseEvent) {
        selectedLocations.getItems().clear();
        resultField.setRoot(null);
    }

    public void conditionsPast6Hours(MouseEvent mouseEvent) {
        resultField.setRoot(new TreeItem<>("Root"));
        List<CityModel> selected = selectedLocations.getItems();
        for (CityModel city : selected) {
            addToLog("Przygotowywanie warunków pogodowych z ostatnich 6h dla "+city.toString() + " ...");
            List<WeatherModel> conditionsPast6Hours = weatherClient.conditionsPast6Hours(city);
            addToLog("Zakończono pobieranie danych");
            addToLog("Wyświetlanie ...");
            displayPastConditions(city, conditionsPast6Hours);
            addToLog("Zakończono przygotowywanie");
        }
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
        resultField.getRoot().getChildren().add(location);
    }

    public void conditionsPast24Hours(MouseEvent mouseEvent) {
        resultField.setRoot(new TreeItem<>("Root"));
        List<CityModel> selected = selectedLocations.getItems();
        for (CityModel city : selected) {
            addToLog("Przygotowywanie warunków pogodowych z ostatnich 24h dla "+city.toString() + " ...");
            List<WeatherModel> conditionsPast24Hours = weatherClient.conditionsPast24Hours(city);
            addToLog("Zakończono pobieranie danych");
            addToLog("Wyświetlanie ...");
            displayPastConditions(city, conditionsPast24Hours);
            addToLog("Zakończono przygotowywanie");
        }
    }

    public void locationDetails(MouseEvent mouseEvent) {
        resultField.setRoot(new TreeItem<>("Root"));
        List<CityModel> selected = selectedLocations.getItems();
        for (CityModel city : selected) {
            CityModel locationDetailed = weatherClient.locationDetails(city);

            TreeItem<String> item = prepareLocationDetailsTreeView(locationDetailed);
            resultField.getRoot().getChildren().add(item);
        }
    }

    public void currentConditions(MouseEvent mouseEvent) {
        resultField.setRoot(new TreeItem<>("Root"));
        List<CityModel> selected = selectedLocations.getItems();
        for (CityModel city : selected) {
            addToLog("Przygotowywanie warunków dla "+city.toString() + " ...");
            WeatherModel weather = weatherClient.currentConditions(city);
            addToLog("Zakończono pobieranie danych");
            addToLog("Wyświetlanie ...");
            TreeItem<String> item = prepareWeatherTreeView(weather, city.toString());
            resultField.getRoot().getChildren().add(item);
            addToLog("Zakończono przygotowywanie");
        }

    }

    //Scenariusz 1: Zapytania asynchronicznie, po zakończeniu wykonywania od razu wypisz.
    public void currentConditionsAsync(MouseEvent mouseEvent) {
        resultField.setRoot(new TreeItem<>("Root"));
        List<CityModel> selected = selectedLocations.getItems();

        for (CityModel city : selected) {
            addToLog("Przygotowywanie warunków dla "+city.toString() + " ...");
            CompletableFuture.runAsync(() -> {
                WeatherModel weather = weatherClient.currentConditions(city);
                TreeItem<String> item = prepareWeatherTreeView(weather, city.toString());
                resultField.getRoot().getChildren().add(item);
                addToLog("Zakończono przygotowywanie warunków dla "+city.toString());
            });
        }
    }

    //Scenariusz 2: Zapytania asynchronicznie, zaczekaj na zakończenie każdego i wtedy wypisz.
    public void currentConditionsAsync1(MouseEvent mouseEvent) {
        resultField.setRoot(new TreeItem<>("Root"));
        List<CityModel> selected = selectedLocations.getItems();

        List<CompletableFuture<WeatherModel>> temperatureFutures = new ArrayList<>();

        for (CityModel city : selected) {
            addToLog("Przygotowywanie warunków dla "+city.toString() + " ...");
            CompletableFuture<WeatherModel> temperatureFuture = CompletableFuture.supplyAsync(() -> {
                WeatherModel weather =  weatherClient.currentConditions(city);
                addToLog("Zakończono przygotowywanie warunków dla "+city.toString());
                return weather;
            });
            temperatureFutures.add(temperatureFuture);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(temperatureFutures.toArray(new CompletableFuture[0]));

        allOf.thenRun(() -> {
            for (CompletableFuture<WeatherModel> temperatureFuture : temperatureFutures) {
                TreeItem<String> item = prepareWeatherTreeView(temperatureFuture.join(), "...");
                resultField.getRoot().getChildren().add(item);
            }
        });
    }

    //Scenariusz 3: Zapytania asynchronicznie zapamiętujące miasto, zaczekaj na zakończenie każdego i wtedy wypisz.
    public void currentConditionsAsync2(MouseEvent mouseEvent) {
        resultField.setRoot(new TreeItem<>("Root"));
        List<CityModel> selected = selectedLocations.getItems();

        List<CompletableFuture<Pair<CityModel,WeatherModel>>> temperatureFutures = new ArrayList<>();

        for (CityModel city : selected) {
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
                resultField.getRoot().getChildren().add(item);
            }
        });
    }

    public void searchResultClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {

            CityModel selectedItem = searchResults.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selectedLocations.getItems().add(selectedItem);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resultField.setRoot(new TreeItem<>("Root"));
        resultField.setShowRoot(false);
    }
}
