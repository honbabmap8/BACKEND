package com.honbabmap.backend.restaurant.repository;

import com.honbabmap.backend.restaurant.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Integer> {
}