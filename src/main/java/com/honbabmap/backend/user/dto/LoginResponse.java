package com.honbabmap.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String accessToken;
    private Long userId; // loginId가 아니라 pk user_id
    private String nickname;
    private Integer honbabLevel;
}
