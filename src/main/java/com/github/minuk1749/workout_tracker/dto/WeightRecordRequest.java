package com.github.minuk1749.workout_tracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class WeightRecordRequest {
    private Double weight;
    private LocalDate recordedAt;
}
