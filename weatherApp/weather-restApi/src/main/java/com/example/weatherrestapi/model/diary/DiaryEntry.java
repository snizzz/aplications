package com.example.weatherrestapi.model.diary;


import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class DiaryEntry {
    @jakarta.persistence.Id
    private Long id;
    private Long entryDatePosixTime;
    private String title;
    private String description;
    private String location;
    private String weatherType;
    private Integer temperature;

    public DiaryEntry(){

    }
    public DiaryEntry(Long id,
                      Long entryDatePosixTime,
                      String title,
                      String description,
                      String location,
                      String weatherType,
                      Integer temperature) {
        this.id = id;
        this.entryDatePosixTime = entryDatePosixTime;
        this.title = title;
        this.description = description;
        this.location = location;
        this.weatherType = weatherType;
        this.temperature = temperature;
    }

}
