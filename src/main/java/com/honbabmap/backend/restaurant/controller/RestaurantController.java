package com.honbabmap.backend.restaurant.controller;

import com.honbabmap.backend.restaurant.dto.RestaurantDetailResponse;
import com.honbabmap.backend.restaurant.dto.RestaurantListResponse;
import com.honbabmap.backend.restaurant.service.RestaurantService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 역 기준 식당 검색
    @GetMapping("/station/{stationId}")
    public ResponseEntity<RestaurantListResponse> restaurantListByStation
    (@PathVariable Integer stationId, @RequestParam Integer honbabLevel) {
        RestaurantListResponse response
                = restaurantService.getRestaurantListByStation(stationId, honbabLevel);
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
