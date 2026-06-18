package com.honbabmap.backend.bookmark.dto;

public record BookmarkListResponse(
        String message,
        BookmarkListData data
) {}