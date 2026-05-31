package com.honbabmap.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public void signup(
            String loginId,
            String password,
            String nickname
    ) {

        // 아이디 중복 확인
        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String encodedPassword =
                passwordEncoder.encode(password);

        UserEntity user = UserEntity.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .nickname(nickname)
                .honbabLevel(1)
                .build();

        userRepository.save(user);
    }

    // 로그인
    public String login(
            String loginId,
            String password
    ) {

        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 아이디입니다."));

        passwordEncoder.matches(
                password,
                user.getPassword()
        );
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return "로그인에 성공했습니다.";
    }
}