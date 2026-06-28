package com.github.minuk1749.workout_tracker.domain.workout.controller;

import com.github.minuk1749.workout_tracker.domain.workout.dto.WorkoutCreateRequest;
import com.github.minuk1749.workout_tracker.domain.workout.dto.WorkoutResponse;
import com.github.minuk1749.workout_tracker.domain.workout.dto.WorkoutUpdateRequest;
import com.github.minuk1749.workout_tracker.domain.workout.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller = HTTP 요청의 입구.
 * URL 과 메서드(GET/POST/PUT/DELETE)를 Service 메서드에 연결만 한다(로직 X).
 *
 * @RestController : 메서드 리턴값을 JSON 으로 자동 변환해서 응답한다.
 * @RequestMapping : 이 컨트롤러의 공통 URL 접두사 (/api/workouts).
 *
 * 명세서 엔드포인트:
 *   GET    /api/workouts            목록 조회
 *   POST   /api/workouts            등록
 *   PUT    /api/workouts/{id}       수정
 *   DELETE /api/workouts/{id}       삭제
 *   (+ GET /api/workouts/{id}       단건 조회, 편의상 추가)
 */
@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    /*
     * ===== userId 임시 처리 안내 =====
     * 원래는 JWT 토큰에서 로그인한 회원 id 를 꺼내야 한다(팀원1 담당).
     * 아직 인증이 없으니, 지금은 쿼리 파라미터 userId 로 직접 받아서 개발/테스트한다.
     *   예) POST /api/workouts?userId=1
     *
     * TODO(JWT 완성 후):
     *   (Long userId = ...param...)  →  (@AuthenticationPrincipal CustomUserDetails user)
     *   처럼 토큰에서 자동으로 꺼내도록 한 줄만 바꾸면 된다.
     */

    /** 등록 → 201 Created */
    @PostMapping
    public ResponseEntity<WorkoutResponse> create(
            @RequestParam Long userId,                       // TODO: JWT 로 교체
            @Valid @RequestBody WorkoutCreateRequest req) {  // @Valid → 검증 자동 수행
        WorkoutResponse res = workoutService.create(userId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    /** 목록 조회 (기간 필터 선택) → 200 OK */
    @GetMapping
    public List<WorkoutResponse> findAll(
            @RequestParam Long userId,                       // TODO: JWT 로 교체
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return workoutService.findAll(userId, from, to);
    }

    /** 단건 조회 → 200 OK */
    @GetMapping("/{id}")
    public WorkoutResponse findOne(
            @RequestParam Long userId,                       // TODO: JWT 로 교체
            @PathVariable Long id) {
        return workoutService.findOne(userId, id);
    }

    /** 수정 → 200 OK */
    @PutMapping("/{id}")
    public WorkoutResponse update(
            @RequestParam Long userId,                       // TODO: JWT 로 교체
            @PathVariable Long id,
            @Valid @RequestBody WorkoutUpdateRequest req) {
        return workoutService.update(userId, id, req);
    }

    /** 삭제 → 204 No Content */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestParam Long userId,                       // TODO: JWT 로 교체
            @PathVariable Long id) {
        workoutService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}
