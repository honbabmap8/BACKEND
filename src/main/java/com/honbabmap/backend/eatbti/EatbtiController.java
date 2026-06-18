package com.honbabmap.backend.eatbti;

import com.honbabmap.backend.eatbti.dto.EatbtiRequest;
import com.honbabmap.backend.eatbti.dto.EatbtiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/signup/eatbti")
public class EatbtiController {

    private final EatbtiService eatbtiService;

    // 1. EatBTI 검사 제출 (POST)
    @PostMapping
    public ResponseEntity<Map<String, Object>> submitEatbti
    (@RequestBody EatbtiRequest request, @AuthenticationPrincipal UserDetails userDetails) {

        List<Integer> answers = request.getAnswers();

        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }
        String loginId = userDetails.getUsername();

        EatbtiResponse.Submit submitResult = eatbtiService.submitEatbti(loginId, answers);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "EatBTI 검사 결과가 성공적으로 반영되었습니다.");
        response.put("data", submitResult);

        return ResponseEntity.ok(response);
    }

    // 2. EatBTI 검사 결과 조회 (GET)
    @GetMapping("/result")
    public ResponseEntity<Map<String, Object>> getEatbtiResult(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }
        String loginId = userDetails.getUsername();

        EatbtiResponse.Result getResult = eatbtiService.getEatbtiResult(loginId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "EatBTI 검사 결과를 성공적으로 불러왔습니다.");
        response.put("data", getResult);

        return ResponseEntity.ok(response);
    }
}