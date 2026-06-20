package com.honbabmap.backend.user;

import com.honbabmap.backend.eatbti.EatbtiEntity;
import com.honbabmap.backend.eatbti.EatbtiRepository;
import com.honbabmap.backend.user.dto.LoginRequest;
import com.honbabmap.backend.user.dto.LoginResponse;
import com.honbabmap.backend.user.dto.MyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

import com.honbabmap.backend.global.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EatbtiRepository eatbtiRepository;

    // 회원가입
    public UserEntity signup(String loginId, String password, String nickname) {

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

        System.out.println("honbabLevel = " + user.getHonbabLevel());

        return userRepository.save(user); // save 메서드: user가 저장된 entity 반환
    }

    // 로그인
    public LoginResponse login(String loginId, String password) {

        // 아이디 존재 여부 확인
        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 아이디입니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createToken(loginId);

        Boolean isNewUser = eatbtiRepository.findByLoginId(loginId).isEmpty();

        LoginResponse response = new LoginResponse("로그인에 성공했습니다.",
                accessToken, user.getUserId(), user.getNickname(), user.getHonbabLevel(), isNewUser);

        return response;
    }

        // 마이페이지 유저 정보 조회
        public MyPageResponse getMyPageInfo(String loginId) {

            UserEntity user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

            return new MyPageResponse(
                    user.getUserId(),
                    user.getNickname(),
                    user.getHonbabLevel()
            );
        }
    }
