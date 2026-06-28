package com.github.minuk1749.workout_tracker.domain.photo.dto;

import com.github.minuk1749.workout_tracker.domain.photo.entity.BodyImage;

import java.time.LocalDate;

public record BodyImageResponse(
        Long id,
        LocalDate recordDate,
        String imageUrl,
        String originalFileName,
        String note
) {
    public static BodyImageResponse from(BodyImage img) {
        return new BodyImageResponse(
                img.getId(),
                img.getRecordDate(),
                img.getImageUrl(),
                img.getOriginalFileName(),
                img.getNote()
        );
    }
}
