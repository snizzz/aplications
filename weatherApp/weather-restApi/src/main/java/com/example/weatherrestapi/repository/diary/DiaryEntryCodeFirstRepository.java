package com.example.weatherrestapi.repository.diary;

import com.example.weatherrestapi.model.diary.DiaryEntry;
import com.example.weatherrestapi.utils.FakeDiaryEntryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class DiaryEntryCodeFirstRepository {

    private final List<DiaryEntry> entries = new ArrayList<>();

    public DiaryEntryCodeFirstRepository(FakeDiaryEntryGenerator faker){
        entries.add(faker.generateFakeObject());
    }

    public List<DiaryEntry> findAll(){
        return entries;
    }

    public Optional<DiaryEntry> findById(Long id){
        return entries.stream()
                .filter(stream -> stream.getId().equals(id))
                .findFirst();
    }

    public DiaryEntry create(DiaryEntry entry){
        entries.add(entry);
        return entry;
    }

    public DiaryEntry update(Long id, DiaryEntry entry){
        DiaryEntry existing = entries.stream()
                .filter(stream -> Objects.equals(stream.getId(), entry.getId()))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Not found"));
        int i = entries.indexOf(existing);
        entries.set(i,entry);
        return entry;
    }

    public DiaryEntry delete(Long id){
        entries.removeIf(stream -> Objects.equals(stream.getId(), id));
        return null;
    }


}
