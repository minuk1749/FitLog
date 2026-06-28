package com.github.minuk1749.workout_tracker.domain.inbody.controller;

import com.github.minuk1749.workout_tracker.domain.inbody.dto.InbodyCreateRequest;
import com.github.minuk1749.workout_tracker.domain.inbody.dto.InbodyResponse;
import com.github.minuk1749.workout_tracker.domain.inbody.dto.InbodyUpdateRequest;
import com.github.minuk1749.workout_tracker.domain.inbody.service.InbodyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 인바디 API (명세서 POST /api/inbody 외 CRUD).
 * userId 는 JWT 완성 전까지 파라미터로 받는다. (TODO: JWT 로 교체)
 */
@RestController
@RequestMapping("/api/inbody")
@RequiredArgsConstructor
public class InbodyController {

    private final InbodyService inbodyService;

    @PostMapping
    public ResponseEntity<InbodyResponse> create(
            @RequestParam Long userId,
            @Valid @RequestBody InbodyCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inbodyService.create(userId, req));
    }

    @GetMapping
    public List<InbodyResponse> findAll(
            @RequestParam Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return inbodyService.findAll(userId, from, to);
    }

    @GetMapping("/{id}")
    public InbodyResponse findOne(@RequestParam Long userId, @PathVariable Long id) {
        return inbodyService.findOne(userId, id);
    }

    @PutMapping("/{id}")
    public InbodyResponse update(
            @RequestParam Long userId,
            @PathVariable Long id,
            @Valid @RequestBody InbodyUpdateRequest req) {
        return inbodyService.update(userId, id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestParam Long userId, @PathVariable Long id) {
        inbodyService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}
