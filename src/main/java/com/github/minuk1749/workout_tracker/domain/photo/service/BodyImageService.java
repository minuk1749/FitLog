package com.github.minuk1749.workout_tracker.domain.photo.service;

import com.github.minuk1749.workout_tracker.domain.photo.dto.BodyImageResponse;
import com.github.minuk1749.workout_tracker.domain.photo.entity.BodyImage;
import com.github.minuk1749.workout_tracker.domain.photo.repository.BodyImageRepository;
import com.github.minuk1749.workout_tracker.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * 눈바디 사진 로직.
 * 파일 저장(FileStorageService) + DB 기록(BodyImageRepository)을 함께 다룬다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BodyImageService {

    private final BodyImageRepository bodyImageRepository;
    private final FileStorageService fileStorageService;

    /** 사진 업로드 (P-401). 파일을 저장하고 그 경로를 DB 에 기록. */
    @Transactional
    public BodyImageResponse upload(Long userId, LocalDate recordDate, String note, MultipartFile file) {
        String imageUrl = fileStorageService.store(file);   // 1) 파일을 폴더에 저장

        BodyImage image = BodyImage.builder()               // 2) 위치를 DB 에 저장
                .userId(userId)
                .recordDate(recordDate)
                .imageUrl(imageUrl)
                .originalFileName(file.getOriginalFilename())
                .note(note)
                .build();

        return BodyImageResponse.from(bodyImageRepository.save(image));
    }

    /** 기간별 목록 조회 (P-402). */
    public List<BodyImageResponse> findAll(Long userId, LocalDate from, LocalDate to) {
        List<BodyImage> images = (from != null && to != null)
                ? bodyImageRepository.findByUserIdAndRecordDateBetweenOrderByRecordDateDesc(userId, from, to)
                : bodyImageRepository.findByUserIdOrderByRecordDateDesc(userId);
        return images.stream().map(BodyImageResponse::from).toList();
    }

    public BodyImageResponse findOne(Long userId, Long id) {
        return BodyImageResponse.from(getOwnedOrThrow(userId, id));
    }

    /** 삭제: DB 기록과 실제 파일을 함께 제거. */
    @Transactional
    public void delete(Long userId, Long id) {
        BodyImage image = getOwnedOrThrow(userId, id);
        bodyImageRepository.delete(image);
        fileStorageService.delete(image.getImageUrl());
    }

    private BodyImage getOwnedOrThrow(Long userId, Long id) {
        BodyImage image = bodyImageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("사진을 찾을 수 없습니다. id=" + id));
        if (!image.getUserId().equals(userId)) {
            throw new NotFoundException("사진을 찾을 수 없습니다. id=" + id);
        }
        return image;
    }
}
