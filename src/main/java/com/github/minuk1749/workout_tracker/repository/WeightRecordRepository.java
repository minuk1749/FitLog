package com.github.minuk1749.workout_tracker.repository;

import com.github.minuk1749.workout_tracker.domain.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {

}
