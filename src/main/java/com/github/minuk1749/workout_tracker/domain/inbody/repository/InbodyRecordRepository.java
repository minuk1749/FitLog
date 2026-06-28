package com.github.minuk1749.workout_tracker.domain.inbody.repository;

import com.github.minuk1749.workout_tracker.domain.inbody.entity.InbodyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InbodyRecordRepository extends JpaRepository<InbodyRecord, Long> {

    // 기간별 조회 (인바디 추이 B-203 의 기반 데이터)
    List<InbodyRecord> findByUserIdAndRecordDateBetweenOrderByRecordDateDesc(
            Long userId, LocalDate from, LocalDate to);

    List<InbodyRecord> findByUserIdOrderByRecordDateDesc(Long userId);
}
