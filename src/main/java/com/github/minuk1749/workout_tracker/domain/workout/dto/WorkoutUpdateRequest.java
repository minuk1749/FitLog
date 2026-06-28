package com.github.minuk1749.workout_tracker.domain.workout.dto;

import com.github.minuk1749.workout_tracker.domain.workout.entity.Intensity;
import com.github.minuk1749.workout_tracker.domain.workout.entity.SportType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * 운동 기록 "수정" 요청 본문 (PUT /api/workouts/{id}).
 *
 * 이번엔 전체 수정(PUT)이라 등록과 필드가 같다.
 * 등록과 분리해 두면, 나중에 수정 규칙만 따로 바뀌어도 서로 영향을 안 준다.
 */
public record WorkoutUpdateRequest(

        @NotNull(message = "운동 날짜는 필수입니다.")
        LocalDate recordDate,

        @NotNull(message = "운동 종류는 필수입니다.")
        SportType sportType,

        @NotNull(message = "운동 시간은 필수입니다.")
        @Positive(message = "운동 시간은 1분 이상이어야 합니다.")
        Integer durationMin,

        Intensity intensity,

        @Size(max = 500, message = "메모는 500자 이하여야 합니다.")
        String memo
) {
}
