package com.honbabmap.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public void signup(String loginId, String password, String nickname) {

        // 아이디 중복 확인
        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        UserEntity user = UserEntity.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .nickname(nickname)
                .honbabLevel(1)
                .build();

        userRepository.save(user);
    }

    // 로그인


    // 리턴 타입을 Map으로 변경!
    public Map<String, Object> login(String loginId, String password) {

        // 1. 아이디 존재 여부 확인
        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 아이디입니다."));

        // 2. 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. JWT 토큰 생성 (현재는 임시 문자열)
        String accessToken = "dummy-jwt-access-token-xyz";

        Map<String, Object> response = new HashMap<>();
        response.put("message", "로그인에 성공했습니다.");
        response.put("accessToken", accessToken);
        response.put("userId", user.getId());
        response.put("nickname", user.getNickname());
        response.put("soloLevel", user.getHonbabLevel());

        return response;
    }
}