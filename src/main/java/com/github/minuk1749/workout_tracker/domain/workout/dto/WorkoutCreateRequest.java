package com.github.minuk1749.workout_tracker.domain.workout.dto;

import com.github.minuk1749.workout_tracker.domain.workout.entity.Intensity;
import com.github.minuk1749.workout_tracker.domain.workout.entity.SportType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * 운동 기록 "등록" 요청 본문 (POST /api/workouts).
 *
 * [DTO 란?] Data Transfer Object. 클라이언트(React)와 주고받는 전용 그릇.
 * Entity 를 그대로 노출하지 않기 위해 따로 만든다(명세서 비기능 요구사항).
 *
 * [record] Java 16+ 문법. 불변 데이터를 짧게 정의. 생성자/getter 등이 자동 생성된다.
 *
 * [@NotNull, @Positive ...] Bean Validation.
 * 컨트롤러에서 @Valid 를 붙이면, 값이 규칙에 안 맞을 때 자동으로 400 에러를 내준다.
 * → 우리가 if 문으로 일일이 검사할 필요가 없다(명세서: Bean Validation 적용).
 */
public record WorkoutCreateRequest(

        @NotNull(message = "운동 날짜는 필수입니다.")
        LocalDate recordDate,

        @NotNull(message = "운동 종류는 필수입니다.")
        SportType sportType,

        @NotNull(message = "운동 시간은 필수입니다.")
        @Positive(message = "운동 시간은 1분 이상이어야 합니다.")
        Integer durationMin,

        // 강도는 선택값이라 @NotNull 을 붙이지 않음
        Intensity intensity,

        @Size(max = 500, message = "메모는 500자 이하여야 합니다.")
        String memo
) {
}
