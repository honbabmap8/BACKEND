package com.honbabmap.backend.eatbti.dto;

import lombok.Builder;
import lombok.Getter;

public class EatbtiResponse {

    // 1. 검사 완료 후 응답 (POST)
    @Getter
    @Builder
    public static class Submit {
        private int userId;
        private int honbabLevel;
    }

    // 2. 결과 상세 조회 응답 (GET)
    @Getter
    @Builder
    public static class Result {
        private int userId;
        private String nickname;
        private int honbabLevel;
        private String levelName;
        private String description;
        private String imageUrl;
    }
}