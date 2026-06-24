package com.github.minuk1749.workout_tracker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "weight_record")
public class WeightRecord {
    @Id // 행을 키값으로 표시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 키값 늘림
    private Long id;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private LocalDate recordedAt;
}
