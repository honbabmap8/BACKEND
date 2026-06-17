package com.honbabmap.backend.restaurant.controller;

import com.honbabmap.backend.restaurant.dto.RestaurantDetailResponse;
import com.honbabmap.backend.restaurant.dto.RestaurantListResponse;
import com.honbabmap.backend.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 역 기준 식당 검색
    @GetMapping("/station/{stationId}")
    public ResponseEntity<RestaurantListResponse> restaurantListByStation
    (@PathVariable Integer stationId,
     @PageableDefault(size = 5) Pageable pageable){ // 페이징 파라미터 추가

        // 토큰 연동 전까지 테스트용 아이디 사용
        String testLoginId = "jaehyeok";

        RestaurantListResponse response
                = restaurantService.getRestaurantListByStation(stationId, testLoginId, pageable);

        return ResponseEntity.ok(response);
    }

    // 식당 상세 정보 조회
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDetailResponse> restaurantDetailByRestaurant
            (@PathVariable Integer restaurantId) {
        RestaurantDetailResponse response
                =restaurantService.getRestaurantDetail(restaurantId);
        return ResponseEntity.ok(response);
    }
}
