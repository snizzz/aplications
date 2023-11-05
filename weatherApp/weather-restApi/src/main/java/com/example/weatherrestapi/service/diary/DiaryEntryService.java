package com.example.weatherrestapi.service.diary;

import com.example.weatherrestapi.model.ServiceResponse;
import com.example.weatherrestapi.model.diary.DiaryEntry;
import com.example.weatherrestapi.repository.diary.DiaryEntryRepository;
import com.example.weatherrestapi.utils.FakeDiaryEntryGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryEntryService {

    private final DiaryEntryRepository repository;

    public DiaryEntryService(DiaryEntryRepository repository, FakeDiaryEntryGenerator faker){
        this.repository = repository;
        this.repository.save(faker.generateFakeObject());
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
            Optional<DiaryEntry> response = repository.findById(id);
            if(response.isPresent()){
                response.get().setTitle(entry.getTitle());
                response.get().setLocation(entry.getLocation());
                response.get().setTemperature(entry.getTemperature());
                response.get().setEntryDatePosixTime(entry.getEntryDatePosixTime());
                response.get().setDescription(entry.getDescription());
                response.get().setWeatherType(entry.getWeatherType());

                repository.save(response.get());
                return new ServiceResponse<>(response.get(), true, "Updated entry");
            }else{
                return new ServiceResponse<>(null, false, "Doesnt exist");
            }

        } catch (Exception e) {
            return new ServiceResponse<>(null, false, e.getMessage());
        }
    }

    public ServiceResponse<DiaryEntry> createEntry(DiaryEntry entry) {
        try {
            DiaryEntry response = repository.save(entry);
            return new ServiceResponse<>(response, true, "Created entry");
        } catch (Exception e) {
            return new ServiceResponse<>(null, false, e.getMessage());
        }
    }

    public ServiceResponse<DiaryEntry> deleteEntry(Long id) {
        try {
            repository.deleteById(id);
            return new ServiceResponse<>(null, true, "Deleted entry");
        } catch (Exception e) {
            return new ServiceResponse<>(null, false, e.getMessage());
        }
    }

}
