package com.honbabmap.backend.global;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // JWT 쓸 거니까 CSRF는 꺼둠
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // H2 콘솔 깨짐 방지
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        // jwt 로그인 방식이므로 서버가 세션을 유지하지 않도록 설정
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/h2-console/**",
                                "/api/users/signup",
                                "/api/users/login"
                        ).permitAll()
                        .requestMatchers("/api/restaurants/*/reviews").authenticated() // 리뷰작성은 로그인 사용자만
                        .requestMatchers("/api/restaurants/**").permitAll() // 그 외에 restaurant 관련 url은 모두 허용
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtAuthentication(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}