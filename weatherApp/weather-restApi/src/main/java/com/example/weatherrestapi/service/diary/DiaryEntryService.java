package com.example.weatherrestapi.service.diary;

import com.example.weatherrestapi.model.ServiceResponse;
import com.example.weatherrestapi.model.diary.DiaryEntry;
import com.example.weatherrestapi.repository.diary.DiaryEntryCodeFirstRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryEntryService {

    private final DiaryEntryCodeFirstRepository repository;

    public DiaryEntryService(DiaryEntryCodeFirstRepository repository){
        this.repository = repository;
    }

    public ServiceResponse<List<DiaryEntry>> findAll() {
        try {
            List<DiaryEntry> response = repository.findAll();
            return new ServiceResponse<>(response, true, "Ok");
        } catch (Exception e) {
            return new ServiceResponse<>(null, false, e.getMessage());
        }
    }

    public ServiceResponse<DiaryEntry> findById(Long id) {
        try{
            Optional<DiaryEntry> optionalDiaryEntry = repository.findById(id);
            return optionalDiaryEntry.map(diaryEntry -> new ServiceResponse<>(diaryEntry, true, "Found entry")).orElseGet(() -> new ServiceResponse<>(null, false, "Entry not found"));
        } catch (Exception e){
            return new ServiceResponse<>(null, false, e.getMessage());
        }
    }


    public ServiceResponse<DiaryEntry> updateEntry(Long id, DiaryEntry entry) {
        try {
            DiaryEntry response = repository.update(id, entry);
            return new ServiceResponse<>(response, true, "Updated entry");
        } catch (Exception e) {
            return new ServiceResponse<>(null, false, e.getMessage());
        }
    }

    public ServiceResponse<DiaryEntry> createEntry(DiaryEntry entry) {
        try {
            DiaryEntry response = repository.create(entry);
            return new ServiceResponse<>(response, true, "Created entry");
        } catch (Exception e) {
            return new ServiceResponse<>(null, false, e.getMessage());
        }
    }

    public ServiceResponse<DiaryEntry> deleteEntry(Long id) {
        try {
            DiaryEntry response = repository.delete(id);
            return new ServiceResponse<>(response, true, "Deleted entry");
        } catch (Exception e) {
            return new ServiceResponse<>(null, false, e.getMessage());
        }
    }

}
