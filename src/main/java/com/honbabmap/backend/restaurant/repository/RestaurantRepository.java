package com.honbabmap.backend.restaurant.repository;

import com.honbabmap.backend.restaurant.entity.RestaurantEntity;
import com.honbabmap.backend.review.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {
    List<RestaurantEntity> findAllByStationId(Integer stationId);
    // SQL의 SELECT * FROM restaurants WHERE station_id = ?; 역할
}
