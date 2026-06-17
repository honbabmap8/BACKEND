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

import com.honbabmap.backend.user.UserRepository;
import com.honbabmap.backend.user.UserEntity;

import lombok.RequiredArgsConstructor;
import org.h2.engine.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final StationRepository stationRepository;
    private final RestFeatRepository restFeatRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    // 페이징 처리를 위해 파라미터에 Pageable 객체를 추가
    public RestaurantListResponse getRestaurantListByStation(Integer stationId, String loginId, Pageable pageable) {
        Integer honbabLevel;
        UserEntity user;

        if(loginId != null) {
            user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            honbabLevel = user.getHonbabLevel();
        }
        else {
            honbabLevel = 1;
        }


        Optional<StationEntity> stationEntityOptional = stationRepository.findById(stationId);
        if (stationEntityOptional.isEmpty())
            throw new IllegalArgumentException("존재하지 않는 지하철역 입니다.");

        StationEntity station = stationEntityOptional.get();

        // DB에서 혼밥 레벨 필터링과 페이징을 한 번에 처리하도록 레포지토리 메서드 변경
        Page<RestaurantEntity> restaurantPage = restaurantRepository
                .findByStationStationIdAndRestSoloLevelLessThanEqual(station.getStationId(), honbabLevel, pageable);

        List<RestaurantListResponse.Restaurant> restaurantListForDto = new ArrayList<>();

        // Page 객체에서 실제 데이터 리스트만 꺼내서 순회 (자바 단의 수동 필터링 로직 삭제)
        for (RestaurantEntity restaurant : restaurantPage.getContent()) {

            RestaurantListResponse.LocationInfo locationInfo
                    = new RestaurantListResponse.LocationInfo
                    (station.getStationId(), station.getStationName(),
                            restaurant.getDistance(), (int) (restaurant.getDistance() / 100));

            // 리뷰태그
            List<TagEntity> top4SelectedReviewTag = getTop4SelectedReviewTag(restaurant.getRestaurantId());

            List<RestaurantListResponse.RestReviewTag> restReviewTagList = new ArrayList<>();

            if(top4SelectedReviewTag != null && !top4SelectedReviewTag.isEmpty()) {
                int limit = Math.min(2, top4SelectedReviewTag.size());
                for(int i=1; i< limit; i++) {
                    RestaurantListResponse.RestReviewTag reviewTag
                            = new RestaurantListResponse.RestReviewTag
                            (top4SelectedReviewTag.get(i-1).getTagId(), top4SelectedReviewTag.get(i-1).getTagName());
                    restReviewTagList.add(reviewTag);
                }
            }

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

        // API 명세서에 맞게 전체 데이터 수, 전체 페이지 수, 현재 페이지 번호를 함께 담음
        RestaurantListResponse.RestaurantData data = new RestaurantListResponse.RestaurantData(
                restaurantPage.getTotalElements(),
                restaurantPage.getTotalPages(),
                restaurantPage.getNumber(),
                restaurantListForDto
        );

        return new RestaurantListResponse("역 근처 식당 목록을 성공적으로 불러왔습니다.", data);
    }

    public RestaurantDetailResponse getRestaurantDetail(Integer restaurantId) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId);

        if (restaurantEntityOptional.isEmpty())
            throw new IllegalArgumentException("존재하지 않는 가게 입니다.");

        RestaurantEntity restaurant = restaurantEntityOptional.get();

        RestaurantDetailResponse.LocationInfo locationInfo
                = new RestaurantDetailResponse.LocationInfo
                (restaurant.getStation().getStationId(), restaurant.getStation().getStationName(),
                        restaurant.getDistance(), (int) (restaurant.getDistance() / 100));

        // 메인 브랜치에 있던 실제 구현된 리뷰태그 로직을 유지
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

        return new RestaurantDetailResponse("식당 상세 정보 조회에 성공했습니다.", restaurantData);
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