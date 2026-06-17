package com.honbabmap.backend.bookmark.repository;

import com.honbabmap.backend.bookmark.entity.BookmarkEntity;
import com.honbabmap.backend.restaurant.entity.RestaurantEntity;
import com.honbabmap.backend.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

    // 특정 사용자가 특정 식당을 이미 찜했는지 확인하는 메서드
    boolean existsByUserAndRestaurant(UserEntity user, RestaurantEntity restaurant);

    // 특정 사용자가 특정 식당에 남긴 찜 기록을 삭제하는 메서드
    void deleteByUserAndRestaurant(UserEntity user, RestaurantEntity restaurant);

    // 특정 사용자의 전체 찜 목록을 페이징 처리하여 가져오는 메서드
    Page<BookmarkEntity> findByUser(UserEntity user, Pageable pageable);
}