package com.example.weatherrestapi.utils;

import com.example.weatherrestapi.model.diary.DiaryEntry;
import com.github.javafaker.Faker;

import javax.management.RuntimeErrorException;
import java.lang.reflect.Field;

public class FakeDiaryEntryGenerator {
    private final Faker faker = new Faker();

    public DiaryEntry generateFakeObject() {
        try {
            var entry =  new DiaryEntry();
            entry.setId(faker.number().randomNumber());
            entry.setEntryDatePosixTime(faker.date().birthday().getTime());
            entry.setTitle(faker.book().title());
            entry.setDescription(faker.weather().description());
            entry.setLocation(faker.address().city());
            entry.setWeatherType(faker.weather().description());
            entry.setTemperature(faker.number().numberBetween(-40,60));

            return entry;
        } catch (Exception e) {
            throw new RuntimeException("Not able to create random diary entry");
        }
    }
}
