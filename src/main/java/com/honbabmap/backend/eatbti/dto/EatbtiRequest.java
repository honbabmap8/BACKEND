package com.honbabmap.backend.eatbti.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class EatbtiRequest {
    // 사용자가 선택한 답변 번호 배열
    private List<Integer> answers;
}