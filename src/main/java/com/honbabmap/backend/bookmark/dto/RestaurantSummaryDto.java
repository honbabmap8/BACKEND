package com.honbabmap.backend.bookmark.dto;

public record RestaurantSummaryDto(
        Integer restaurantId,
        String name,
        String imageUrl,
        String representativeMenu,
        Integer restSoloLevel
) {}