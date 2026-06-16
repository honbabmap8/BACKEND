package com.honbabmap.backend.review.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewRequest {

    @NotEmpty(message = "태그를 하나도 선택하지 않았거나 필수 값이 누락되었습니다.")
    @Size(max = 12, message = "태그는 최대 12개 이하로 선택해야 합니다.")
    private List<Integer> selectedTagsArray;
}