package com.github.minuk1749.workout_tracker.domain.body.service;

import com.github.minuk1749.workout_tracker.domain.body.dto.BodyProfileRequest;
import com.github.minuk1749.workout_tracker.domain.body.dto.BodyProfileResponse;
import com.github.minuk1749.workout_tracker.domain.body.entity.BodyProfile;
import com.github.minuk1749.workout_tracker.domain.body.repository.BodyProfileRepository;
import com.github.minuk1749.workout_tracker.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 신체정보 로직.
 * 회원당 1개라서 CRUD 대신 "내 정보 조회 + 저장(upsert)" 두 가지만 제공한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BodyProfileService {

    private final BodyProfileRepository bodyProfileRepository;

    /** 내 신체정보 조회. 아직 저장 안 했으면 404. */
    public BodyProfileResponse getMine(Long userId) {
        BodyProfile profile = bodyProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("신체정보가 아직 등록되지 않았습니다."));
        return BodyProfileResponse.from(profile);
    }

    /**
     * 저장(upsert): 있으면 수정, 없으면 새로 생성.
     * 그래서 프론트는 "신체정보 저장" 버튼 하나로 등록/수정을 모두 처리할 수 있다.
     */
    @Transactional
    public BodyProfileResponse save(Long userId, BodyProfileRequest req) {
        BodyProfile profile = bodyProfileRepository.findByUserId(userId)
                .map(existing -> {                          // 이미 있으면 → 수정
                    existing.update(req.height(), req.age(), req.gender());
                    return existing;
                })
                .orElseGet(() -> bodyProfileRepository.save(  // 없으면 → 새로 저장
                        BodyProfile.builder()
                                .userId(userId)
                                .height(req.height())
                                .age(req.age())
                                .gender(req.gender())
                                .build()
                ));
        return BodyProfileResponse.from(profile);
    }
}
