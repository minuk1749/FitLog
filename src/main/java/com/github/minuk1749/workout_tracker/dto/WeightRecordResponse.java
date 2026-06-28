package com.github.minuk1749.workout_tracker.dto;

import com.github.minuk1749.workout_tracker.domain.WeightRecord;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WeightRecordResponse {

    private final Long id;
    private final Double weight;
    private final LocalDate recordedAt;
    /* 이 세개의 변수는 현재는 WeightRecord에 있는 변수 전부이지만
       만약 WeightRecord에 변수가 여러개 만들어졌다면 그 중 원하는 것만 골라서 적으면 됨  */

    public WeightRecordResponse(WeightRecord weightRecord) {
        this.id = weightRecord.getId();
        this.weight = weightRecord.getWeight();
        this.recordedAt = weightRecord.getRecordedAt();
    }
}
