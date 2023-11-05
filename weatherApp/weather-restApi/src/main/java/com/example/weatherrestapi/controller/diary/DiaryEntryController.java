package com.example.weatherrestapi.controller.diary;

import com.example.weatherrestapi.model.ServiceResponse;
import com.example.weatherrestapi.model.diary.DiaryEntry;
import com.example.weatherrestapi.repository.diary.DiaryEntryCodeFirstRepository;
import com.example.weatherrestapi.service.diary.DiaryEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diary")
public class DiaryEntryController {
    private final DiaryEntryService service;

    public DiaryEntryController(DiaryEntryService service){
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<ServiceResponse<List<DiaryEntry>>> findAll(){
        ServiceResponse<List<DiaryEntry>> response = service.findAll();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<DiaryEntry>> findById(@PathVariable Long id) {
        ServiceResponse<DiaryEntry> response = service.findById(id);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ServiceResponse<DiaryEntry>> createEntry(@RequestBody DiaryEntry entry) {
        ServiceResponse<DiaryEntry> response = service.createEntry(entry);
        System.out.println(entry);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse<DiaryEntry>> updateEntry(@PathVariable Long id, @RequestBody DiaryEntry entry) {
        System.out.println(entry);
        ServiceResponse<DiaryEntry> response = service.updateEntry(id,entry);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse<DiaryEntry>> deleteEntry(@PathVariable Long id) {
        ServiceResponse<DiaryEntry> response = service.deleteEntry(id);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
