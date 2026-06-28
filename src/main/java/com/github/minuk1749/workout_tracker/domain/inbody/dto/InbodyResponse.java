package com.github.minuk1749.workout_tracker.domain.inbody.dto;

import com.github.minuk1749.workout_tracker.domain.inbody.entity.InbodyRecord;

import java.time.LocalDate;

/** 인바디 기록 응답. Entity → DTO 변환. */
public record InbodyResponse(
        Long id,
        LocalDate recordDate,
        Double skeletalMuscle,
        Double bodyFatMass,
        Double bodyFatPct,
        Double bmi,
        Double bmr
) {
    public static InbodyResponse from(InbodyRecord r) {
        return new InbodyResponse(
                r.getId(),
                r.getRecordDate(),
                r.getSkeletalMuscle(),
                r.getBodyFatMass(),
                r.getBodyFatPct(),
                r.getBmi(),
                r.getBmr()
        );
    }
}
