package com.honbabmap.backend.user;

import com.honbabmap.backend.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<SignupResponse> signUp(@Valid @RequestBody SignupRequest request) {
        UserEntity savedUser = userService.signup(request.getLoginId(), request.getPassword(), request.getNickname());

        SignupResponse response = new SignupResponse("회원가입이 성공적으로 완료되었습니다.",
                        savedUser.getUserId(), savedUser.getNickname());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = userService.login(request.getLoginId(), request.getPassword());

        return ResponseEntity.ok(response);
    }
}