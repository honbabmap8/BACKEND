package com.honbabmap.backend.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {
    private String message;
    private ReviewData data;

    @Getter
    @Builder
    public static class ReviewData {
        private Integer reviewId;
        private Integer restaurantId;
        private String writerNickname;
        private String createdAt;
    }
}