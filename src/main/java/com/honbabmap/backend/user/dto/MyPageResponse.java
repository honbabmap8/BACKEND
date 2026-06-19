package com.honbabmap.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageResponse {
    private Integer userId;
    private String nickname;
    private int honbabLevel;
}