package com.github.minuk1749.workout_tracker.domain.body.dto;

import com.github.minuk1749.workout_tracker.domain.body.entity.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 신체정보 저장(upsert) 요청 (PUT /api/body).
 * 키·나이는 양수, 성별은 필수.
 */
public record BodyProfileRequest(

        @NotNull(message = "키는 필수입니다.")
        @Positive(message = "키는 0보다 커야 합니다.")
        Double height,

        @NotNull(message = "나이는 필수입니다.")
        @Positive(message = "나이는 0보다 커야 합니다.")
        Integer age,

        @NotNull(message = "성별은 필수입니다.")
        Gender gender
) {
}
