package com.honbabmap.backend.bookmark.dto;

import java.util.List;

public record BookmarkListData(
        List<RestaurantSummaryDto> restaurantList
) {}