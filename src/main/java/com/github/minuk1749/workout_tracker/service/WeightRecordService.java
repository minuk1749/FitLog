package com.github.minuk1749.workout_tracker.service;

import com.github.minuk1749.workout_tracker.dto.WeightRecordRequest;
import com.github.minuk1749.workout_tracker.domain.WeightRecord;
import com.github.minuk1749.workout_tracker.dto.WeightRecordRequest;
import com.github.minuk1749.workout_tracker.dto.WeightRecordResponse;
import com.github.minuk1749.workout_tracker.repository.WeightRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeightRecordService {

    private final WeightRecordRepository weightRecordRepository;

    public List<WeightRecordResponse> getAllWeightRecords () { //GET 기능
        return weightRecordRepository.findAll()
                .stream()
                .map(WeightRecordResponse::new)
                .collect(Collectors.toList());
    }

    public WeightRecordResponse saveWeightRecord(WeightRecordRequest request) { // POST 기능
        WeightRecord weightRecord = new WeightRecord(request.getWeight(), request.getRecordedAt());
        WeightRecord saved = weightRecordRepository.save(weightRecord); // 기본으로 있는 save기능 활용
        return new WeightRecordResponse(saved);
    }

    public void deleteWeightRecord(Long id) { //DELETE 기능
        weightRecordRepository.deleteById(id);
    }

    @Transactional
    public WeightRecordResponse updateWeightRecord(Long id, WeightRecordRequest request) { // PATCH 기능
        WeightRecord weightRecord = weightRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("체중 기록을 찾을 수 없습니다"));
        weightRecord.update(request.getWeight(), request.getRecordedAt());
        return new WeightRecordResponse(weightRecord);
    }
}
