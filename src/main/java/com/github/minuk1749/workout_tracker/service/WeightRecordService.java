package com.github.minuk1749.workout_tracker.service;

import com.github.minuk1749.workout_tracker.dto.WeightRecordRequest;
import com.github.minuk1749.workout_tracker.domain.WeightRecord;
import com.github.minuk1749.workout_tracker.dto.WeightRecordRequest;
import com.github.minuk1749.workout_tracker.dto.WeightRecordResponse;
import com.github.minuk1749.workout_tracker.repository.WeightRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeightRecordService {

    private final WeightRecordRepository weightRecordRepository;

    public List<WeightRecordResponse> getAllWeightRecords () {
        return weightRecordRepository.findAll()
                .stream()
                .map(WeightRecordResponse::new)
                .collect(Collectors.toList());
    }

    public WeightRecordResponse saveWeightRecord(WeightRecordRequest request) {
        WeightRecord weightRecord = new WeightRecord(request.getWeight(), request.getRecordedAt());
        WeightRecord saved = weightRecordRepository.save(weightRecord); // 기본으로 있는 save기능 활용
        return new WeightRecordResponse(saved);
    }
}
