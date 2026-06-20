package com.honbabmap.backend.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {
    private Integer userId;
    private String nickname;
    private int honbabLevel;
}