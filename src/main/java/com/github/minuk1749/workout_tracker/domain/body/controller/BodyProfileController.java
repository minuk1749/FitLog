package com.github.minuk1749.workout_tracker.domain.body.controller;

import com.github.minuk1749.workout_tracker.domain.body.dto.BodyProfileRequest;
import com.github.minuk1749.workout_tracker.domain.body.dto.BodyProfileResponse;
import com.github.minuk1749.workout_tracker.domain.body.service.BodyProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 신체정보 API (명세서 B-201).
 *   GET /api/body          내 신체정보 조회
 *   PUT /api/body          신체정보 저장(등록 또는 수정)
 *
 * userId 는 JWT 완성 전까지 파라미터로 받는다. (TODO: JWT 로 교체)
 */
@RestController
@RequestMapping("/api/body")
@RequiredArgsConstructor
public class BodyProfileController {

    private final BodyProfileService bodyProfileService;

    @GetMapping
    public BodyProfileResponse getMine(@RequestParam Long userId) {
        return bodyProfileService.getMine(userId);
    }

    @PutMapping
    public BodyProfileResponse save(
            @RequestParam Long userId,
            @Valid @RequestBody BodyProfileRequest req) {
        return bodyProfileService.save(userId, req);
    }
}
