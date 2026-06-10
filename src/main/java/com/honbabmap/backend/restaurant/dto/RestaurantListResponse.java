package com.honbabmap.backend.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantListResponse {
    private String message;
    private RestaurantData data;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RestaurantData {
        private List<Restaurant> restaurantList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Restaurant {
        private Integer restaurantId;
        private String restaurantName; // 식당 이름
        private String imageUrl;
        private Integer restLevel;
        private String representativeMenu;

        private LocationInfo locationInfo;
        private List<RestReviewTag> restReviewTagList; // 리뷰 태그는 4개
    }

    @Getter
    @AllArgsConstructor
    public static class RestReviewTag { // 리뷰태그 클래스
        private Integer tagId;
        private String tagName;
    }

    @Getter
    @AllArgsConstructor
    public static class LocationInfo {
        private Integer stationId;
        private String stationName;
        private double distance;
        private Integer time;
    }
}
