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
        private Long restaurantId;
        private String name; // 식당 이름
        private String imageUrl;
        private int restLevel;
        private String representativeMenu;

        private LocationInfo locationInfo;
        private List<RestReviewTag> restReviewTagList; // 리뷰 태그는 4개
    }

    @Getter
    @AllArgsConstructor
    public static class RestReviewTag { // 리뷰태그 클래스
        private Long tagId;
        private String tagName;
    }

    @Getter
    @AllArgsConstructor
    public static class LocationInfo {
        private Long stationId;
        private String stationName;
        private double distance;
        private int time;
    }
}
