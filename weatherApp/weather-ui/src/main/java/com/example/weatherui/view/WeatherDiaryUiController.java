package com.example.weatherui.view;

import com.example.weatherrestapi.model.diary.DiaryEntry;
import com.example.weatherui.viewModel.WeatherDiaryUiViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherDiaryUiController {

    @FXML
    public TextField idField;
    @FXML
    public TextField titleField;
    @FXML
    public TextField descriptionField;
    @FXML
    public TextField locationField;
    @FXML
    public TextField customWeatherTypeField;
    @FXML
    public TextField temperatureField;
    @FXML
    public TextField entryIdField;
    @FXML
    public Pagination foundEntryListView;
    @FXML
    public Pagination pagination;
    private WeatherDiaryUiViewModel viewModel;
    public void init(WeatherDiaryUiViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void addEntry(ActionEvent actionEvent) {
        DiaryEntry newEntry = new DiaryEntry();
        newEntry.setId((long) Integer.parseInt(idField.getText().replaceAll("[\\D]", "")));
        newEntry.setEntryDatePosixTime(11111111L);
        newEntry.setTitle(titleField.getText());
        newEntry.setDescription(descriptionField.getText());
        newEntry.setLocation(locationField.getText());
        newEntry.setWeatherType(customWeatherTypeField.getText());
        newEntry.setTemperature(Integer.parseInt(temperatureField.getText().replaceAll("[\\D]", "")));
        viewModel.addEntry(newEntry);
    }

    public void clearFields(ActionEvent actionEvent) {
        idField.clear();
        titleField.clear();
        descriptionField.clear();
        locationField.clear();
        customWeatherTypeField.clear();
        temperatureField.clear();
    }

    public void updateEntry(ActionEvent actionEvent) {
        DiaryEntry newEntry = new DiaryEntry();
        newEntry.setId((long) Integer.parseInt(idField.getText().replaceAll("[\\D]", "")));
        newEntry.setEntryDatePosixTime(11111111L);
        newEntry.setTitle(titleField.getText());
        newEntry.setDescription(descriptionField.getText());
        newEntry.setLocation(locationField.getText());
        newEntry.setWeatherType(customWeatherTypeField.getText());
        newEntry.setTemperature(Integer.parseInt(temperatureField.getText().replaceAll("[\\D]", "")));
        viewModel.updateEntry(newEntry, Integer.parseInt(idField.getText().replaceAll("[\\D]", "")));
    }

    public void getAllEntries(ActionEvent actionEvent) {
        try {
            List<DiaryEntry> entries = viewModel.getAllEntries();
            System.out.println(entries);
            pagination.setPageCount((int) Math.ceil((double) entries.size() / itemsPerPage()));
            pagination.setStyle("-fx-border-color:red;");

            pagination.setPageFactory(pageIndex -> createPage(entries, pageIndex));
        }catch (Exception ignored){

        }
    }

    private VBox createPage(List<DiaryEntry> entries, int pageIndex) {
        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();
        int pageLimit = Math.min(page + itemsPerPage(), entries.size());

        for (int i = page; i < pageLimit; i++) {
            DiaryEntry entry = entries.get(i);
            VBox element = creteDiaryEntryTree(entry);
            box.getChildren().add(element);
        }
        return box;
    }

    private VBox creteDiaryEntryTree(DiaryEntry entry) {
        VBox element = new VBox();

        Label text = new Label(entry.getTitle());

        TreeView<String> treeView = new TreeView<>();
        TreeItem<String> root = new TreeItem<>();

        TreeItem<String> id = new TreeItem<>("Id: " + entry.getId().toString());
        root.getChildren().add(id);

        TreeItem<String> time = new TreeItem<>("Creation time: " + entry.getEntryDatePosixTime().toString());
        root.getChildren().add(time);

        TreeItem<String> description = new TreeItem<>("Description");
        TreeItem<String> description_text = new TreeItem<>(entry.getDescription());
        description.getChildren().add(description_text);
        root.getChildren().add(description);

        TreeItem<String> location = new TreeItem<>("Location: " + entry.getLocation());
        root.getChildren().add(location);

        TreeItem<String> weatherType = new TreeItem<>("Weather type: " + entry.getWeatherType());
        root.getChildren().add(weatherType);

        TreeItem<String> temperature = new TreeItem<>("Temperatura: " + entry.getTemperature().toString());
        root.getChildren().add(temperature);

        treeView.setRoot(root);
        treeView.setShowRoot(false);

        element.getChildren().addAll(text, treeView);

        return element;
    }

    private int itemsPerPage() {
        return 1;
    }

    public void findEntryById(ActionEvent actionEvent) {
        DiaryEntry entry = viewModel.findEntryById(Integer.parseInt(entryIdField.getText().replaceAll("[\\D]", "")));
        List<DiaryEntry> list = new ArrayList<>();
        list.add(entry);
        foundEntryListView.setPageCount((int) Math.ceil((double) list.size() / itemsPerPage()));
        foundEntryListView.setStyle("-fx-border-color:red;");

        foundEntryListView.setPageFactory(pageIndex -> createPage(list, pageIndex));
    }

    public void deleteEntry(ActionEvent actionEvent) {
        viewModel.deleteEntry(Integer.parseInt(entryIdField.getText().replaceAll("[\\D]", "")));
    }

}
