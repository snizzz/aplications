package com.example.weatherrestapi.model.diary;


import lombok.Data;

import java.util.Objects;

@Data
public class DiaryEntry {
    private Long id;
    private Long entryDatePosixTime;
    private String title;
    private String description;
    private String location;
    private String weatherType;
    private Integer temperature;

    public DiaryEntry(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaryEntry that = (DiaryEntry) o;
        return id == that.id && entryDatePosixTime == that.entryDatePosixTime && temperature == that.temperature && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(location, that.location) && Objects.equals(weatherType, that.weatherType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entryDatePosixTime, title, description, location, weatherType, temperature);
    }
}
