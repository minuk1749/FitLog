package com.github.minuk1749.workout_tracker.domain.inbody.service;

import com.github.minuk1749.workout_tracker.domain.inbody.dto.InbodyCreateRequest;
import com.github.minuk1749.workout_tracker.domain.inbody.dto.InbodyResponse;
import com.github.minuk1749.workout_tracker.domain.inbody.dto.InbodyUpdateRequest;
import com.github.minuk1749.workout_tracker.domain.inbody.entity.InbodyRecord;
import com.github.minuk1749.workout_tracker.domain.inbody.repository.InbodyRecordRepository;
import com.github.minuk1749.workout_tracker.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/** 인바디 비즈니스 로직. WorkoutService 와 구조 동일. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InbodyService {

    private final InbodyRecordRepository inbodyRecordRepository;

    @Transactional
    public InbodyResponse create(Long userId, InbodyCreateRequest req) {
        InbodyRecord record = InbodyRecord.builder()
                .userId(userId)
                .recordDate(req.recordDate())
                .skeletalMuscle(req.skeletalMuscle())
                .bodyFatMass(req.bodyFatMass())
                .bodyFatPct(req.bodyFatPct())
                .bmi(req.bmi())
                .bmr(req.bmr())
                .build();
        return InbodyResponse.from(inbodyRecordRepository.save(record));
    }

    public List<InbodyResponse> findAll(Long userId, LocalDate from, LocalDate to) {
        List<InbodyRecord> records = (from != null && to != null)
                ? inbodyRecordRepository.findByUserIdAndRecordDateBetweenOrderByRecordDateDesc(userId, from, to)
                : inbodyRecordRepository.findByUserIdOrderByRecordDateDesc(userId);
        return records.stream().map(InbodyResponse::from).toList();
    }

    public InbodyResponse findOne(Long userId, Long id) {
        return InbodyResponse.from(getOwnedOrThrow(userId, id));
    }

    @Transactional
    public InbodyResponse update(Long userId, Long id, InbodyUpdateRequest req) {
        InbodyRecord record = getOwnedOrThrow(userId, id);
        record.update(req.recordDate(), req.skeletalMuscle(), req.bodyFatMass(),
                req.bodyFatPct(), req.bmi(), req.bmr());
        return InbodyResponse.from(record);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        inbodyRecordRepository.delete(getOwnedOrThrow(userId, id));
    }

    private InbodyRecord getOwnedOrThrow(Long userId, Long id) {
        InbodyRecord record = inbodyRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("인바디 기록을 찾을 수 없습니다. id=" + id));
        if (!record.getUserId().equals(userId)) {
            throw new NotFoundException("인바디 기록을 찾을 수 없습니다. id=" + id);
        }
        return record;
    }
}
