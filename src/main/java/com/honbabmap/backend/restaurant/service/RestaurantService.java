package com.honbabmap.backend.restaurant.service;

import com.honbabmap.backend.restaurant.dto.RestaurantDetailResponse;
import com.honbabmap.backend.restaurant.dto.RestaurantListResponse;
import com.honbabmap.backend.restaurant.entity.RestaurantEntity;
import com.honbabmap.backend.restaurant.entity.StationEntity;
import com.honbabmap.backend.restaurant.repository.*;
//import com.honbabmap.backend.review.repository.ReviewRepository;
//import com.honbabmap.backend.review.repository.ReviewTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    // Repository
    private final RestaurantRepository restaurantRepository;
    private final StationRepository stationRepository;
    // private final ReviewRepository reviewRepository;
    // private final ReviewTagRepository reviewTagRepository;

    public RestaurantListResponse getRestaurantListByStation(Integer stationId, Integer honbabLevel) {
        Optional<StationEntity> stationEntityOptional = stationRepository.findById(stationId);

        if (!stationEntityOptional.isPresent())
            throw new IllegalArgumentException("존재하지 않는 지하철역 입니다.");

        StationEntity station = stationEntityOptional.get(); // 지하철역 테이블에 존재하는 지하철역임.

        List<RestaurantEntity> restaurantEntityList = restaurantRepository.findAllByStationStationId(station.getStationId());
        // stationId에 해당하는 레스토랑 리스트를 가져옴.

        List<RestaurantListResponse.Restaurant> restaurantListForDto = new ArrayList<>();
        // response dto에 레스토랑 리스트를 담을 리스트를 생성

        for (RestaurantEntity restaurant : restaurantEntityList) {
            // 사용자 레벨과 같거나 하위 레벨만 조회할 것.
            if (honbabLevel < restaurant.getRestSoloLevel())
                continue;

            // locationInfo
            RestaurantListResponse.LocationInfo locationInfo
                    = new RestaurantListResponse.LocationInfo
                    (station.getStationId(), station.getStationName(),
                            restaurant.getDistance(), (int) (restaurant.getDistance() / 100)); // 속도는 100m/s

            // 태그 구현 코드는 나중에 구현, 지금은 임시 데이터
            RestaurantListResponse.RestReviewTag reviewTag = new RestaurantListResponse.RestReviewTag(1, "임시태그");
            List<RestaurantListResponse.RestReviewTag> restReviewTagList = new ArrayList<>();
            restReviewTagList.add(reviewTag);
            restReviewTagList.add(reviewTag);

            RestaurantListResponse.Restaurant rest = RestaurantListResponse.Restaurant
                    .builder()
                    .restaurantId(restaurant.getRestaurantId())
                    .restaurantName(restaurant.getRestaurantName())
                    .imageUrl(restaurant.getImg())
                    .restSoloLevel(restaurant.getRestSoloLevel())
                    .representativeMenu(restaurant.getMenu())
                    .locationInfo(locationInfo)
                    .restReviewTagList(restReviewTagList).build();

            restaurantListForDto.add(rest);
        }

        RestaurantListResponse.RestaurantData data = new RestaurantListResponse.RestaurantData(restaurantListForDto);

        RestaurantListResponse response = new RestaurantListResponse("역 근처 식당 목록을 성공적으로 불러왔습니다.", data);

        return response;
    }

    public RestaurantDetailResponse getRestaurantDetail(Integer restaurantId) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId);

        if (!restaurantEntityOptional.isPresent())
            throw new IllegalArgumentException("존재하지 않는 가게 입니다.");

        RestaurantEntity restaurant = restaurantEntityOptional.get();

        // locationInfo
        RestaurantDetailResponse.LocationInfo locationInfo
                = new RestaurantDetailResponse.LocationInfo
                (restaurant.getStation().getStationId(), restaurant.getStation().getStationName(),
                        restaurant.getDistance(), (int) (restaurant.getDistance() / 100)); // 속도는 100m/s

        // 태그 구현 코드는 나중에 구현, 지금은 임시 데이터
        RestaurantDetailResponse.RestReviewTag reviewTag = new RestaurantDetailResponse.RestReviewTag(1, "임시태그");
        List<RestaurantDetailResponse.RestReviewTag> restReviewTagList = new ArrayList<>();
        restReviewTagList.add(reviewTag);
        restReviewTagList.add(reviewTag);
        restReviewTagList.add(reviewTag);
        restReviewTagList.add(reviewTag);

        RestaurantDetailResponse.RestFeatTag featTag = new RestaurantDetailResponse.RestFeatTag(1, "임시 특징 태그");
        List<RestaurantDetailResponse.RestFeatTag> featTagList = new ArrayList<>();
        featTagList.add(featTag);
        featTagList.add(featTag);

        RestaurantDetailResponse.RestaurantData restaurantData = RestaurantDetailResponse.RestaurantData
                .builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantName(restaurant.getRestaurantName())
                .imageUrl(restaurant.getImg())
                .restSoloLevel(restaurant.getRestSoloLevel())
                .representativeMenu(restaurant.getMenu())
                .restaurantDetail(restaurant.getRestaurantDetail())
                .locationInfo(locationInfo)
                .restFeatureList(featTagList)
                .restReviewTagList(restReviewTagList)
                .build();

        RestaurantDetailResponse response = new RestaurantDetailResponse("식당 상세 정보 조회에 성공했습니다.", restaurantData);

        return response;
    }
}
