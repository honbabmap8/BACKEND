package com.honbabmap.backend.global;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey; // Key에서 SecretKey로 변경
    private final long validityInMilliseconds;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey, // secretKey: 인증키, expire-length: 토큰 유효 시간
            @Value("${jwt.expire-length}") long validityInMilliseconds) {

        // Keys.hmacShaKeyFor 결과물을 바로 SecretKey 타입으로 받습니다.
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    // 1. 토큰 생성 메서드
    public String createToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .subject(userId)         // 사용되지 않는 setClaims() 대신 바로 subject 설정
                .issuedAt(now)
                .expiration(validity)
                .signWith(secretKey)     // 알고리즘(HS256)은 키 크기를 보고 자동 지정되므로 생략 가능합니다.
                .compact();
    }

    // 2. 토큰 검증 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // 3. 사용자 정보 추출 메서드
    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()            // getBody() 대신 getPayload()로 변경되었습니다.
                .getSubject();
    }
}