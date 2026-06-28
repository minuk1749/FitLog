package com.github.minuk1749.workout_tracker.domain.body.dto;

import com.github.minuk1749.workout_tracker.domain.body.entity.BodyProfile;
import com.github.minuk1749.workout_tracker.domain.body.entity.Gender;

public record BodyProfileResponse(
        Long id,
        Double height,
        Integer age,
        Gender gender
) {
    public static BodyProfileResponse from(BodyProfile p) {
        return new BodyProfileResponse(p.getId(), p.getHeight(), p.getAge(), p.getGender());
    }
}
