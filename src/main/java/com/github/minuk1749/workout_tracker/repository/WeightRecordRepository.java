package com.github.minuk1749.workout_tracker.repository;

import com.github.minuk1749.workout_tracker.domain.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {
    //JpaRepository를 상속받기만 해도 저장,조회,삭제기능이 자동으로 생김
}
