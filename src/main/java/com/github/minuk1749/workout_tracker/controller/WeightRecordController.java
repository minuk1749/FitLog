package com.github.minuk1749.workout_tracker.controller;

import com.github.minuk1749.workout_tracker.dto.WeightRecordRequest;
import com.github.minuk1749.workout_tracker.dto.WeightRecordResponse;
import com.github.minuk1749.workout_tracker.service.WeightRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PatchMapping;

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
    public WeightRecordResponse saveWeightRecord(@RequestBody WeightRecordRequest request) { // POST 기능
        return weightRecordService.saveWeightRecord(request);
    }

    @DeleteMapping("/{id}")
    public void deleteWeightRecord(@PathVariable Long id) { // DELETE 기능
        weightRecordService.deleteWeightRecord(id);
    }

    @PatchMapping("/{id}")
    public WeightRecordResponse updateWeightRecord(@PathVariable Long id, @RequestBody WeightRecordRequest request) {
        return weightRecordService.updateWeightRecord(id, request);
    }
}
