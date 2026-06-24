package com.github.minuk1749.workout_tracker.service;

import com.github.minuk1749.workout_tracker.domain.WeightRecord;
import com.github.minuk1749.workout_tracker.repository.WeightRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeightRecordService {

    private final WeightRecordRepository weightRecordRepository;

    public List<WeightRecord> getAllRecords() {
        return weightRecordRepository.findAll();
    } // DB에 있는 체중기록 전부를 불러옴
}
