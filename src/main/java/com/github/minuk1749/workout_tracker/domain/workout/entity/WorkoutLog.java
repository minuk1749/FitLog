package com.github.minuk1749.workout_tracker.domain.workout.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 운동 세션 1건 = WorkoutLog (명세서 데이터 모델 / E-501).
 *
 * [Entity 란?]
 * DB 테이블 한 줄과 1:1로 대응되는 자바 객체다.
 * @Entity 가 붙으면 JPA(Hibernate)가 이 클래스를 보고 테이블을 만들고(읽고/쓰고) 관리해준다.
 *
 * [중요 - 명세서 규칙]
 * "Entity 를 컨트롤러에서 직접 노출 금지" → 그래서 요청/응답은 별도 DTO 로 받고 내보낸다.
 * Entity 는 DB 안쪽 전용이라고 생각하면 된다.
 */
@Entity
@Table(name = "workout_log")
@Getter                                                // Lombok: 모든 필드의 getter 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)     // JPA 는 기본 생성자가 필요. 외부에서 new 막으려고 PROTECTED.
public class WorkoutLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL auto_increment
    private Long id;

    /**
     * 어떤 회원의 기록인지.
     *
     * 원래는 User 엔티티와 @ManyToOne 으로 연결해야 하지만(명세서 User 1:N),
     * User 엔티티는 팀원1이 만드는 중이라 아직 없을 수 있다.
     * 그래서 지금은 그냥 user_id 값(Long)만 저장해두고 독립적으로 개발한다.
     *
     * TODO(팀원1의 User·JWT 완성 후):
     *   private Long userId;  →  @ManyToOne User user; 로 교체하고
     *   userId 는 컨트롤러에서 JWT 토큰으로부터 꺼내 채운다.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;                       // 운동한 날짜

    @Enumerated(EnumType.STRING)                        // enum 을 숫자가 아닌 문자열로 저장(안전)
    @Column(name = "sport_type", nullable = false, length = 20)
    private SportType sportType;                        // 운동 종류

    @Column(name = "duration_min", nullable = false)
    private Integer durationMin;                        // 운동 시간(분)

    @Enumerated(EnumType.STRING)
    @Column(name = "intensity", length = 10)
    private Intensity intensity;                        // 운동 강도(선택)

    @Column(name = "memo", length = 500)
    private String memo;                               // 메모(선택)

    /**
     * 생성은 빌더로만. 필수값을 빠뜨리지 않게 강제하는 효과가 있다.
     */
    @Builder
    public WorkoutLog(Long userId, LocalDate recordDate, SportType sportType,
                      Integer durationMin, Intensity intensity, String memo) {
        this.userId = userId;
        this.recordDate = recordDate;
        this.sportType = sportType;
        this.durationMin = durationMin;
        this.intensity = intensity;
        this.memo = memo;
    }

    /**
     * 수정용 메서드. (setter 를 막고 의미있는 메서드로 바꾸는 게 좋은 습관)
     * Service 에서 이 메서드를 호출하면, JPA 가 트랜잭션 끝날 때 변경을 자동 감지(dirty checking)해
     * UPDATE 쿼리를 날려준다. 즉 repository.save() 를 또 부를 필요가 없다.
     */
    public void update(LocalDate recordDate, SportType sportType,
                       Integer durationMin, Intensity intensity, String memo) {
        this.recordDate = recordDate;
        this.sportType = sportType;
        this.durationMin = durationMin;
        this.intensity = intensity;
        this.memo = memo;
    }
}
