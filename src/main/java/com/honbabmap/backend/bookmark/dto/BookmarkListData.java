package com.honbabmap.backend.bookmark.dto;

import java.util.List;

public record BookmarkListData(
        long totalElements,
        int totalPages,
        int currentPage,
        List<RestaurantSummaryDto> restaurantList
) {}