package com.github.minuk1749.workout_tracker.domain.photo.controller;

import com.github.minuk1749.workout_tracker.domain.photo.dto.BodyImageResponse;
import com.github.minuk1749.workout_tracker.domain.photo.service.BodyImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * 눈바디 사진 API (명세서 P-401/402 / POST /api/body-photos).
 *
 * [다른 도메인과 가장 큰 차이]
 * 사진은 JSON 이 아니라 **파일**이라, multipart/form-data 로 받는다.
 *  - @RequestBody(X)  →  @RequestParam("file") MultipartFile(O)
 *  - 텍스트 필드(recordDate, note)도 form-data 의 일부로 함께 받는다.
 *
 * userId 는 JWT 완성 전까지 파라미터로 받는다. (TODO: JWT 로 교체)
 */
@RestController
@RequestMapping("/api/body-photos")
@RequiredArgsConstructor
public class BodyImageController {

    private final BodyImageService bodyImageService;

    /** 업로드 → 201. (Swagger 나 Postman 의 form-data 로 file 첨부) */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BodyImageResponse> upload(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate recordDate,
            @RequestParam(required = false) String note,
            @RequestParam("file") MultipartFile file) {
        BodyImageResponse res = bodyImageService.upload(userId, recordDate, note, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    /** 기간별 목록 조회 → 200 */
    @GetMapping
    public List<BodyImageResponse> findAll(
            @RequestParam Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return bodyImageService.findAll(userId, from, to);
    }

    /** 단건 조회 → 200 */
    @GetMapping("/{id}")
    public BodyImageResponse findOne(@RequestParam Long userId, @PathVariable Long id) {
        return bodyImageService.findOne(userId, id);
    }

    /** 삭제 → 204 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestParam Long userId, @PathVariable Long id) {
        bodyImageService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}
