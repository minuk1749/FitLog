package com.github.minuk1749.workout_tracker.controller;

import com.github.minuk1749.workout_tracker.domain.WeightRecord;
import com.github.minuk1749.workout_tracker.service.WeightRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weight")
public class WeightRecordController {

    private final WeightRecordService weightRecordService;

    @GetMapping
    public List<WeightRecord> getAllWeightRecords() {
        return weightRecordService.getAllRecords();
    }
}
