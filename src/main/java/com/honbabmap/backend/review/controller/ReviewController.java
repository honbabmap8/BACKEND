package com.honbabmap.backend.review.controller;

import com.honbabmap.backend.review.dto.ReviewRequest;
import com.honbabmap.backend.review.dto.ReviewResponse;
import com.honbabmap.backend.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{restaurantId}/reviews")
    public ResponseEntity<ReviewResponse> createReview(
            @PathVariable Integer restaurantId,
            @Valid @RequestBody ReviewRequest request) {

        // 토큰 검증을 임시로 꺼두었으므로, 테스트를 위해 DB에 존재하는 실제 유저 아이디를 직접 주입
        String loginId = "jaehyeok";

        // 식당 ID, 요청 데이터, 그리고 강제로 지정한 유저 ID를 서비스 로직으로 전달
        ReviewResponse response = reviewService.createReview(restaurantId, request, loginId);

        // 로직이 성공적으로 수행되면 201 Created 상태 코드와 함께 응답 DTO를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}