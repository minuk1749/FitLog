package com.github.minuk1749.workout_tracker.domain.workout.entity;

/**
 * 운동 종류 (명세서 3-5 / E-501: 복싱·유도·주짓수·런닝·헬스·맨몸운동).
 *
 * enum 으로 두면 DB 에는 문자열("BOXING" 등)로 저장되고,
 * 잘못된 값이 들어오는 걸 컴파일 단계에서 막아준다.
 */
public enum SportType {
    BOXING,       // 복싱
    JUDO,         // 유도
    JIUJITSU,     // 주짓수
    RUNNING,      // 런닝
    GYM,          // 헬스(웨이트)
    BODYWEIGHT    // 맨몸운동
}
