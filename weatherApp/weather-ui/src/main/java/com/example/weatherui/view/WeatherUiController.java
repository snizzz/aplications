package com.example.weatherui.view;

import com.example.weatherclient.models.CityModel;
import com.example.weatherui.viewModel.WeatherUiViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(com.example.weatherclient.ClientConfiguration.class)
public class WeatherUiController{
    @FXML
    public ListView<CityModel> searchResults;
    @FXML
    public Button searchButton;
    @FXML
    public TextField searchText;
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
    public TreeView<String> resultField;
    @FXML
    public Label resultFieldLabel1;
    @FXML
    public TextArea loggerField;
    @FXML
    public Button openDiaryButton;
    private WeatherUiViewModel viewModel;

    public void search(MouseEvent mouseEvent) {
        viewModel.search();
    }

    public void resetSelected(MouseEvent mouseEvent) {
        viewModel.resetSelected();
    }

    public void conditionsPast6Hours(MouseEvent mouseEvent) {
        viewModel.conditionsPast6Hours();
    }

    public void conditionsPast24Hours(MouseEvent mouseEvent) {
        viewModel.conditionsPast24Hours();
    }

    public void locationDetails(MouseEvent mouseEvent) {
        viewModel.locationDetails();
    }

    public void currentConditions(MouseEvent mouseEvent) {
        viewModel.currentConditions();
    }

    //Scenariusz 1: Zapytania asynchronicznie, po zakończeniu wykonywania od razu wypisz.
    public void currentConditionsAsync(MouseEvent mouseEvent) {
        viewModel.currentConditionsAsync();
    }

    //Scenariusz 2: Zapytania asynchronicznie, zaczekaj na zakończenie każdego i wtedy wypisz.
    public void currentConditionsAsync1(MouseEvent mouseEvent) {
        viewModel.currentConditionsAsync1();
    }

    //Scenariusz 3: Zapytania asynchronicznie zapamiętujące miasto, zaczekaj na zakończenie każdego i wtedy wypisz.
    public void currentConditionsAsync2(MouseEvent mouseEvent) {
        viewModel.currentConditionsAsync2();
    }

    public void searchResultClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {

            CityModel selectedItem = searchResults.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                viewModel.searchResultClicked(selectedItem);
            }
        }
    }

    public void bindButtons(){
        currentConditionsButton.disableProperty().bind(viewModel.currentConditionsButtonProperty());
        resetSelectedButton.disableProperty().bind(viewModel.resetSelectedButtonProperty());
        conditionsPast6HoursButton.disableProperty().bind(viewModel.conditionsPast6HoursButtonProperty());
        conditionsPast24HoursButton.disableProperty().bind(viewModel.conditionsPast24HoursButtonProperty());
        locationDetailsButton.disableProperty().bind(viewModel.locationDetailsButtonProperty());
        currentConditionsAsyncButton.disableProperty().bind(viewModel.currentConditionsAsyncButtonProperty());
        currentConditionsAsync1Button.disableProperty().bind(viewModel.currentConditionsAsync1ButtonProperty());
        currentConditionsAsync2Button.disableProperty().bind(viewModel.currentConditionsAsync2ButtonProperty());
    }

    public void init(WeatherUiViewModel viewModel) {
        this.viewModel = viewModel;

        bindButtons();

        searchText.textProperty().bindBidirectional(viewModel.searchTextProperty());

        resultField.rootProperty().bind(viewModel.resultFieldRootProperty());
        resultField.setShowRoot(false);

        selectedLocations.setItems(viewModel.getSelectedLocations());
        searchResults.setItems(viewModel.getSearchResults());

        loggerField.textProperty().bind(viewModel.loggerFieldProperty());
    }

    public void openWeatherDiary(MouseEvent mouseEvent) {
        viewModel.openDiary();
    }
}
