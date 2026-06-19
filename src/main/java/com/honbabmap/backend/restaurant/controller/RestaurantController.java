package com.honbabmap.backend.restaurant.controller;

import com.honbabmap.backend.restaurant.dto.RestaurantDetailResponse;
import com.honbabmap.backend.restaurant.dto.RestaurantListResponse;
import com.honbabmap.backend.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    (@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer stationId) {
        String loginId;
        if(userDetails != null) { // 로그인한 사용자인 경우 로그인 아이디 저장
            loginId = userDetails.getUsername();
        }
        else loginId = null; // 비로그인 사용자인 경우 loginId는 null

        RestaurantListResponse response
                = restaurantService.getRestaurantListByStation(loginId, stationId);

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
