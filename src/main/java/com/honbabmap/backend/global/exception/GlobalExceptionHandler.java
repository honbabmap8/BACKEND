package com.honbabmap.backend.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {

        Map<String, String> errorResponse = new HashMap<>();
        // UserService에서 던진 "존재하지 않는 아이디입니다." 등의 메시지가 e.getMessage()를 통해 들어감
        errorResponse.put("message", e.getMessage());

        // 프론트엔드 명세서에 맞게 404 Not Found 상태 코드로 반환
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());

        // 비밀번호 불일치 등 인증 실패 시 명세서에 맞게 401 Unauthorized 코드로 반환합니다.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}