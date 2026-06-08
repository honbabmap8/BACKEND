package com.honbabmap.backend.review.repository;

import com.honbabmap.backend.review.entity.ReviewTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewTagRepository extends JpaRepository<ReviewTagEntity, Long> {
    List<ReviewTagEntity> findAllByReviewRestaurantId(Long restaurantId);
    // SQL의 SELECT * FROM review_tag WHERE rest_id = ?; 역할
}
