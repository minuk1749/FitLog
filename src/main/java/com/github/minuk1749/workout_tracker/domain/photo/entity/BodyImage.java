package com.github.minuk1749.workout_tracker.domain.photo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 눈바디 사진 1장 (명세서 P-401/402 / 데이터 모델 BodyImage).
 *
 * [핵심] DB 에는 사진의 "바이트"가 아니라 **저장 위치(imageUrl)** 만 넣는다.
 * 실제 이미지 파일은 서버의 폴더(uploads/)에 저장한다. (FileStorageService 담당)
 * → DB 는 가볍게, 이미지는 파일시스템에. 흔한 방식이다.
 */
@Entity
@Table(name = "body_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;          // 촬영 날짜

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;               // 접근 경로 (예: /uploads/xxxx.jpg)

    @Column(name = "original_file_name", length = 255)
    private String originalFileName;       // 사용자가 올린 원래 파일명(표시용)

    @Column(name = "note", length = 300)
    private String note;                   // 메모(선택)

    @Builder
    public BodyImage(Long userId, LocalDate recordDate, String imageUrl,
                     String originalFileName, String note) {
        this.userId = userId;
        this.recordDate = recordDate;
        this.imageUrl = imageUrl;
        this.originalFileName = originalFileName;
        this.note = note;
    }
}
