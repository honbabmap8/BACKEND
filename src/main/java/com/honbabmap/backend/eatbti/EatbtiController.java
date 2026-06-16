package com.honbabmap.backend.eatbti;

import com.honbabmap.backend.eatbti.dto.EatbtiRequest;
import com.honbabmap.backend.eatbti.dto.EatbtiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Object>> submitEatbti(@RequestBody EatbtiRequest request) {

        List<Integer> answers = request.getAnswers();

        // JWT 필터 연동 전까지 통신 테스트를 위해 임시 아이디를 강제로 고정
        int userId = 1;

        EatbtiResponse.Submit submitResult = eatbtiService.submitEatbti(userId, answers);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "EatBTI 검사 결과가 성공적으로 반영되었습니다.");
        response.put("data", submitResult);

        return ResponseEntity.ok(response);
    }

    // 2. EatBTI 검사 결과 조회 (GET)
    @GetMapping("/result")
    public ResponseEntity<Map<String, Object>> getEatbtiResult() {

        // 여기도 마찬가지로 임시 아이디를 고정하여 사용
        int userId = 1;

        EatbtiResponse.Result getResult = eatbtiService.getEatbtiResult(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "EatBTI 검사 결과를 성공적으로 불러왔습니다.");
        response.put("data", getResult);

        return ResponseEntity.ok(response);
    }
}