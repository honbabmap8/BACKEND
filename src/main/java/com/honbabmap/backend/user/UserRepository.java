package com.honbabmap.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByLoginId(String loginId);
    // loginId로 사용자를 조회할 때 사용.
}