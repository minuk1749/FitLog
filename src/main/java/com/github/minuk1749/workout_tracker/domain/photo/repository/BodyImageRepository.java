package com.github.minuk1749.workout_tracker.domain.photo.repository;

import com.github.minuk1749.workout_tracker.domain.photo.entity.BodyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BodyImageRepository extends JpaRepository<BodyImage, Long> {

    List<BodyImage> findByUserIdAndRecordDateBetweenOrderByRecordDateDesc(
            Long userId, LocalDate from, LocalDate to);

    List<BodyImage> findByUserIdOrderByRecordDateDesc(Long userId);
}
