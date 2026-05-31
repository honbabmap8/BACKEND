package com.honbabmap.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public String signup(
            @RequestBody Map<String, String> request
    ) {

        userService.signup(
                request.get("loginId"),
                request.get("password"),
                request.get("nickname")
        );

        return "회원가입이 성공적으로 완료되었습니다";
    }

    // 로그인
    @PostMapping("/login")
    public String login(
            @RequestBody Map<String, String> request
    ) {

        return userService.login(
                request.get("loginId"),
                request.get("password")
        );
    }
}