package com.honbabmap.backend.restaurant.repository;

import com.honbabmap.backend.restaurant.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

    // 기존: findAllByStationStationId(...)
    // 추가할 메서드: 역 ID가 같고, 식당의 혼밥 레벨이 사용자의 혼밥 레벨 이하인 데이터만 페이징해서 가져옵니다.
    Page<RestaurantEntity> findByStationStationIdAndRestSoloLevelLessThanEqual(Integer stationId, Integer honbabLevel, Pageable pageable);

}
