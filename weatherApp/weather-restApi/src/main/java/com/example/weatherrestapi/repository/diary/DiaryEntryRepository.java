package com.example.weatherrestapi.repository.diary;

import com.example.weatherrestapi.model.diary.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {
}
