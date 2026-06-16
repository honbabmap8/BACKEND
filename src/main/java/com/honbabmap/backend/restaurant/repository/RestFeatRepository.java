package com.honbabmap.backend.restaurant.repository;

import com.honbabmap.backend.restaurant.entity.RestFeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestFeatRepository extends JpaRepository<RestFeatEntity, Integer> {
    List<RestFeatEntity> findByRestaurantId_RestaurantId(Integer restId);

}
