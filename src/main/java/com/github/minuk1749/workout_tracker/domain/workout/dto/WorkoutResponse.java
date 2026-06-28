package com.github.minuk1749.workout_tracker.domain.workout.dto;

import com.github.minuk1749.workout_tracker.domain.workout.entity.Intensity;
import com.github.minuk1749.workout_tracker.domain.workout.entity.SportType;
import com.github.minuk1749.workout_tracker.domain.workout.entity.WorkoutLog;

import java.time.LocalDate;

/**
 * 운동 기록 "응답" 본문. 서버 → 클라이언트(React)로 내보내는 그릇.
 *
 * Entity(WorkoutLog)를 그대로 주지 않고, 필요한 필드만 골라 이 DTO 로 변환해서 보낸다.
 * (예: password 같은 민감 필드나 내부 연관관계가 그대로 새어나가는 걸 막는다.)
 */
public record WorkoutResponse(
        Long id,
        LocalDate recordDate,
        SportType sportType,
        Integer durationMin,
        Intensity intensity,
        String memo
) {
    /**
     * Entity → Response DTO 변환용 정적 팩토리 메서드.
     * Service 에서 WorkoutResponse.from(entity) 처럼 깔끔하게 쓴다.
     */
    public static WorkoutResponse from(WorkoutLog log) {
        return new WorkoutResponse(
                log.getId(),
                log.getRecordDate(),
                log.getSportType(),
                log.getDurationMin(),
                log.getIntensity(),
                log.getMemo()
        );
    }
}
