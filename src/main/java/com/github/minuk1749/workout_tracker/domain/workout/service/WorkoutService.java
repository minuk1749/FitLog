package com.github.minuk1749.workout_tracker.domain.workout.service;

import com.github.minuk1749.workout_tracker.domain.workout.dto.WorkoutCreateRequest;
import com.github.minuk1749.workout_tracker.domain.workout.dto.WorkoutResponse;
import com.github.minuk1749.workout_tracker.domain.workout.dto.WorkoutUpdateRequest;
import com.github.minuk1749.workout_tracker.domain.workout.entity.WorkoutLog;
import com.github.minuk1749.workout_tracker.domain.workout.repository.WorkoutLogRepository;
import com.github.minuk1749.workout_tracker.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service = "실제 일을 처리하는" 핵심 비즈니스 로직 계층.
 *
 * 역할 분담:
 *  - Controller: HTTP 요청을 받고 응답을 돌려주는 통역사 (얇게 유지)
 *  - Service   : 진짜 로직 (저장/조회/검증/변환) ← 지금 이 파일
 *  - Repository: DB 와 대화
 *
 * @Service        : 스프링이 이 클래스를 관리(빈 등록)하게 한다.
 * @RequiredArgsConstructor : final 필드를 받는 생성자를 Lombok 이 자동 생성 → 의존성 주입(DI).
 * @Transactional  : 메서드 하나를 하나의 DB 트랜잭션으로 묶는다(실패 시 자동 롤백).
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)   // 기본은 읽기 전용. 쓰는 메서드에만 따로 @Transactional 을 단다.
public class WorkoutService {

    private final WorkoutLogRepository workoutLogRepository;

    /** 등록 (POST /api/workouts) */
    @Transactional
    public WorkoutResponse create(Long userId, WorkoutCreateRequest req) {
        WorkoutLog log = WorkoutLog.builder()
                .userId(userId)
                .recordDate(req.recordDate())
                .sportType(req.sportType())
                .durationMin(req.durationMin())
                .intensity(req.intensity())
                .memo(req.memo())
                .build();

        WorkoutLog saved = workoutLogRepository.save(log);
        return WorkoutResponse.from(saved);
    }

    /** 목록 조회 (GET /api/workouts?from=&to=). from/to 가 없으면 전체. */
    public List<WorkoutResponse> findAll(Long userId, LocalDate from, LocalDate to) {
        List<WorkoutLog> logs = (from != null && to != null)
                ? workoutLogRepository.findByUserIdAndRecordDateBetweenOrderByRecordDateDesc(userId, from, to)
                : workoutLogRepository.findByUserIdOrderByRecordDateDesc(userId);

        return logs.stream()
                .map(WorkoutResponse::from)   // 각 Entity 를 Response DTO 로 변환
                .toList();
    }

    /** 단건 조회 (GET /api/workouts/{id}) */
    public WorkoutResponse findOne(Long userId, Long id) {
        WorkoutLog log = getOwnedLogOrThrow(userId, id);
        return WorkoutResponse.from(log);
    }

    /** 수정 (PUT /api/workouts/{id}) */
    @Transactional
    public WorkoutResponse update(Long userId, Long id, WorkoutUpdateRequest req) {
        WorkoutLog log = getOwnedLogOrThrow(userId, id);

        // Entity 의 update 메서드 호출만 하면, 트랜잭션이 끝날 때 JPA 가 알아서 UPDATE 한다.
        log.update(req.recordDate(), req.sportType(), req.durationMin(), req.intensity(), req.memo());
        return WorkoutResponse.from(log);
    }

    /** 삭제 (DELETE /api/workouts/{id}) */
    @Transactional
    public void delete(Long userId, Long id) {
        WorkoutLog log = getOwnedLogOrThrow(userId, id);
        workoutLogRepository.delete(log);
    }

    /**
     * 공통: id 로 기록을 찾되,
     *  - 없으면 404(NotFoundException)
     *  - 남의 기록이면 접근 못 하게 막는다(본인 것만).
     */
    private WorkoutLog getOwnedLogOrThrow(Long userId, Long id) {
        WorkoutLog log = workoutLogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("운동 기록을 찾을 수 없습니다. id=" + id));

        if (!log.getUserId().equals(userId)) {
            throw new NotFoundException("운동 기록을 찾을 수 없습니다. id=" + id);
        }
        return log;
    }
}
