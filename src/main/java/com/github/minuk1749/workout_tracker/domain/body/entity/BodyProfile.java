package com.github.minuk1749.workout_tracker.domain.body.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 신체 기본정보 (명세서 B-201 / 데이터 모델 BodyProfile).
 *
 * [운동·인바디와 다른 점]
 * User 와 **1:1** 관계다. 즉 회원당 행이 하나뿐이다(매번 새로 쌓지 않음).
 * 그래서 "등록/조회/수정"이 아니라 "내 신체정보 조회 + 저장(upsert)" 형태로 다룬다.
 *  - 처음 저장 → INSERT
 *  - 이미 있으면 → UPDATE
 *
 * user_id 에 unique 제약을 걸어 회원당 1행을 보장한다.
 */
@Entity
@Table(name = "body_profile", uniqueConstraints = {
        @UniqueConstraint(name = "uk_body_profile_user", columnNames = "user_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "height")
    private Double height;     // 키 (cm)

    @Column(name = "age")
    private Integer age;       // 나이

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;     // 성별

    @Builder
    public BodyProfile(Long userId, Double height, Integer age, Gender gender) {
        this.userId = userId;
        this.height = height;
        this.age = age;
        this.gender = gender;
    }

    public void update(Double height, Integer age, Gender gender) {
        this.height = height;
        this.age = age;
        this.gender = gender;
    }
}
