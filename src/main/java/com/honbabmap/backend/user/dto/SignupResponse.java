package com.honbabmap.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponse {
    private String message;
    private Long userId;
    private String nickname;
}
