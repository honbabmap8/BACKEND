package com.honbabmap.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public String signup(@RequestBody Map<String, String> request) {

        userService.signup(
                request.get("loginId"),
                request.get("password"),
                request.get("nickname")
        );

        return "회원가입이 성공적으로 완료되었습니다";
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {

        // 서비스에서 만든 회원 정보와 토큰이 담긴 Map을 받아옵니다.
        Map<String, Object> response = userService.login(
                request.get("loginId"),
                request.get("password")
        );

        // ResponseEntity로 감싸서 예쁜 JSON으로 리턴해줍니다.
        return ResponseEntity.ok(response);
    }
}