package com.github.minuk1749.workout_tracker.controller;

import com.github.minuk1749.workout_tracker.dto.WeightRecordRequest;
import com.github.minuk1749.workout_tracker.dto.WeightRecordResponse;
import com.github.minuk1749.workout_tracker.service.WeightRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weights")
public class WeightRecordController {

    private final WeightRecordService weightRecordService;

    @GetMapping
    public List<WeightRecordResponse> getAllWeightRecords() {
        return weightRecordService.getAllWeightRecords();
    }

    @PostMapping
    public WeightRecordResponse saveWeightRecord(@RequestBody WeightRecordRequest request) {
        return weightRecordService.saveWeightRecord(request);
    }
}
