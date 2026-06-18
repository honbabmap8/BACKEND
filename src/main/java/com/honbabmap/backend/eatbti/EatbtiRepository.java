package com.honbabmap.backend.eatbti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EatbtiRepository extends JpaRepository<EatbtiEntity, String> {

    Optional<EatbtiEntity> findByLoginId(String loginId);
}