package com.github.minuk1749.workout_tracker.domain.inbody.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

/** 인바디 기록 수정 요청 (PUT /api/inbody/{id}). 등록과 동일 형식. */
public record InbodyUpdateRequest(

        @NotNull(message = "측정 날짜는 필수입니다.")
        LocalDate recordDate,

        @PositiveOrZero(message = "골격근량은 0 이상이어야 합니다.")
        Double skeletalMuscle,

        @PositiveOrZero(message = "체지방량은 0 이상이어야 합니다.")
        Double bodyFatMass,

        @PositiveOrZero(message = "체지방률은 0 이상이어야 합니다.")
        Double bodyFatPct,

        @PositiveOrZero(message = "BMI는 0 이상이어야 합니다.")
        Double bmi,

        @PositiveOrZero(message = "기초대사량은 0 이상이어야 합니다.")
        Double bmr
) {
}
