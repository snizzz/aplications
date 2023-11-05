package com.example.weatherui.viewModel;

import com.example.weatherclient.WeatherDiaryClient;
import com.example.weatherrestapi.model.diary.DiaryEntry;

import java.util.List;

public class WeatherDiaryUiViewModel {

    private final WeatherDiaryClient client;
    public WeatherDiaryUiViewModel(WeatherDiaryClient weatherDiaryClient) {
        this.client = weatherDiaryClient;
    }

    public void addEntry(DiaryEntry newEntry) {
        client.addEntry(newEntry);
    }

    public List<DiaryEntry> getAllEntries() {
        return client.getAllEntries();
    }

    public void updateEntry(DiaryEntry newEntry, Integer integer) {
        client.updateEntry(integer, newEntry);
    }

    public DiaryEntry findEntryById(Integer integer) {
        return client.findEntryById(integer);
    }

    public DiaryEntry deleteEntry(Integer integer) {
        return client.deleteEntry(integer);
    }
}
