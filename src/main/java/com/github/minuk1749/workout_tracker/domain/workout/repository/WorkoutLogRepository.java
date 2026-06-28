package com.github.minuk1749.workout_tracker.domain.workout.repository;

import com.github.minuk1749.workout_tracker.domain.workout.entity.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository = DB 접근 계층.
 *
 * [마법처럼 보이는 부분]
 * JpaRepository 를 상속(extends)하기만 하면
 * save(), findById(), findAll(), delete() 같은 기본 CRUD 메서드를
 * Spring Data JPA 가 구현체를 자동으로 만들어 준다. 우리가 짤 코드가 없다.
 *
 * <WorkoutLog, Long> = <어떤 Entity, 그 Entity 의 PK 타입>
 *
 * [쿼리 메서드]
 * 아래처럼 규칙에 맞는 "메서드 이름"만 적으면 JPA 가 이름을 해석해 쿼리를 만들어 준다.
 * findByUserId... → SELECT * FROM workout_log WHERE user_id = ? ...
 */
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {

    // 특정 회원의 운동 기록을, 날짜 범위로 필터해서 최신순으로 조회 (E-502 / GET /api/workouts)
    List<WorkoutLog> findByUserIdAndRecordDateBetweenOrderByRecordDateDesc(
            Long userId, LocalDate from, LocalDate to);

    // 날짜 필터 없이 특정 회원 전체 조회 (최신순)
    List<WorkoutLog> findByUserIdOrderByRecordDateDesc(Long userId);
}
