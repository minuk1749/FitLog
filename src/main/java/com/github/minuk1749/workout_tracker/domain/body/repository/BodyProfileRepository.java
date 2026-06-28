package com.github.minuk1749.workout_tracker.domain.body.repository;

import com.github.minuk1749.workout_tracker.domain.body.entity.BodyProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BodyProfileRepository extends JpaRepository<BodyProfile, Long> {

    // 회원당 1개라서 단건(Optional)으로 조회한다.
    Optional<BodyProfile> findByUserId(Long userId);
}
