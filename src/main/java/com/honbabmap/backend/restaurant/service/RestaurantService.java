package com.honbabmap.backend.restaurant.service;

import com.honbabmap.backend.restaurant.dto.RestaurantDetailResponse;
import com.honbabmap.backend.restaurant.dto.RestaurantListResponse;
import com.honbabmap.backend.user.UserRepository;
import com.honbabmap.backend.restaurant.entity.RestFeatEntity;
import com.honbabmap.backend.restaurant.entity.RestaurantEntity;
import com.honbabmap.backend.restaurant.entity.StationEntity;
import com.honbabmap.backend.restaurant.repository.RestFeatRepository;
import com.honbabmap.backend.restaurant.repository.RestaurantRepository;
import com.honbabmap.backend.restaurant.repository.StationRepository;
import com.honbabmap.backend.review.entity.ReviewTagEntity;
import com.honbabmap.backend.review.entity.TagEntity;
import com.honbabmap.backend.review.repository.ReviewRepository;
import com.honbabmap.backend.review.repository.ReviewTagRepository;
import com.honbabmap.backend.review.repository.TagRepository;
import com.honbabmap.backend.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    // Repository
    private final RestaurantRepository restaurantRepository;
    private final StationRepository stationRepository;
    private final RestFeatRepository restFeatRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public RestaurantListResponse getRestaurantListByStation(String loginId, Integer stationId) {
        UserEntity user = userRepository.findByLoginId(loginId)
                .orElse(null);

        Integer honbabLevel;

        if(user == null) {
            honbabLevel = 1;
        }
        else {
            honbabLevel = user.getHonbabLevel();
        }

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

        // 리뷰태그
        List<TagEntity> top4SelectedReviewTag = getTop4SelectedReviewTag(restaurantId);

        List<RestaurantDetailResponse.RestReviewTag> restReviewTagList = new ArrayList<>();

        for(int i=1; i<=top4SelectedReviewTag.size(); i++) {
            RestaurantDetailResponse.RestReviewTag reviewTag
                    = new RestaurantDetailResponse.RestReviewTag
                    (top4SelectedReviewTag.get(i-1).getTagId(), top4SelectedReviewTag.get(i-1).getTagName());
            restReviewTagList.add(reviewTag);
        }

        List<RestFeatEntity> restFeat = restFeatRepository.findByRestaurantId_RestaurantId(restaurantId);

        RestaurantDetailResponse.RestFeatTag featTag;
        List<RestaurantDetailResponse.RestFeatTag> featTagList = new ArrayList<>();

        for(int i = 0; i < 2; i++) {
            featTag = new RestaurantDetailResponse.RestFeatTag
                    (restFeat.get(i).getFeatureId().getFeatureId(), restFeat.get(i).getFeatureId().getFeatureName());
            featTagList.add(featTag);
        }

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

    public List<TagEntity> getTop4SelectedReviewTag(Integer restaurantId) {
        List<ReviewTagEntity> reviewTagEntityList
                = reviewTagRepository.findAllByReviewRestaurantId(restaurantId);
        List<TagEntity> tagEntityList = tagRepository.findAll();

        int tagSize = tagEntityList.size();
        int[] tagCountArray = new int[tagSize + 1];

        for(ReviewTagEntity reviewTag : reviewTagEntityList) {
            tagCountArray[reviewTag.getTag().getTagId()-1]++;
        }

        int[] top4TagIds = IntStream.range(0, tagCountArray.length)
                .filter(i -> tagCountArray[i] > 0) // 리뷰가 1개 이상인 것만
                .boxed() // 인덱스(int)들을 Integer 객체로 변환(정렬을 위해)
                .sorted((o1, o2) -> Integer.compare(tagCountArray[o2], tagCountArray[o1]))
                .limit(4) // 정렬된 인덱스 중 앞에서부터 딱 4개만
                .mapToInt(Integer::intValue) // 다시 int 배열로 바꾸기 위해 언박싱
                .toArray();

        List<TagEntity> top4SelectedTag = new ArrayList<>();

        TagEntity tagEntity;

        for(int i = 0; i<top4TagIds.length; i++) {
            tagEntity = tagEntityList.get(top4TagIds[i]);
            top4SelectedTag.add(tagEntity);
        }

        return top4SelectedTag;
    }
}
