package com.honbabmap.backend.review.repository;

import com.honbabmap.backend.review.entity.ReviewTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewTagRepository extends JpaRepository<ReviewTagEntity, Integer> {

}