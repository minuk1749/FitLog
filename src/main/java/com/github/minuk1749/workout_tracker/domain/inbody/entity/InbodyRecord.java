package com.github.minuk1749.workout_tracker.domain.inbody.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 인바디 측정 기록 1건 (명세서 B-202 / 데이터 모델 InbodyRecord).
 * 구조는 WorkoutLog 와 완전히 동일하다. 필드만 인바디 수치로 바뀐 것뿐.
 *
 * User 와 1:N 이지만, 지금은 userId(Long)만 저장한다 (JWT 완성 전 독립 개발).
 * TODO(JWT 후): userId → @ManyToOne User user 로 교체.
 */
@Entity
@Table(name = "inbody_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InbodyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;            // 측정 날짜

    @Column(name = "skeletal_muscle")
    private Double skeletalMuscle;           // 골격근량 (kg)

    @Column(name = "body_fat_mass")
    private Double bodyFatMass;              // 체지방량 (kg)

    @Column(name = "body_fat_pct")
    private Double bodyFatPct;               // 체지방률 (%)

    @Column(name = "bmi")
    private Double bmi;                      // 체질량지수

    @Column(name = "bmr")
    private Double bmr;                      // 기초대사량 (kcal)

    @Builder
    public InbodyRecord(Long userId, LocalDate recordDate, Double skeletalMuscle,
                        Double bodyFatMass, Double bodyFatPct, Double bmi, Double bmr) {
        this.userId = userId;
        this.recordDate = recordDate;
        this.skeletalMuscle = skeletalMuscle;
        this.bodyFatMass = bodyFatMass;
        this.bodyFatPct = bodyFatPct;
        this.bmi = bmi;
        this.bmr = bmr;
    }

    public void update(LocalDate recordDate, Double skeletalMuscle, Double bodyFatMass,
                       Double bodyFatPct, Double bmi, Double bmr) {
        this.recordDate = recordDate;
        this.skeletalMuscle = skeletalMuscle;
        this.bodyFatMass = bodyFatMass;
        this.bodyFatPct = bodyFatPct;
        this.bmi = bmi;
        this.bmr = bmr;
    }
}
