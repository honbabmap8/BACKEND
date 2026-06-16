package com.honbabmap.backend.review.service;

import com.honbabmap.backend.review.dto.ReviewRequest;
import com.honbabmap.backend.review.dto.ReviewResponse;
import com.honbabmap.backend.review.entity.ReviewEntity;
import com.honbabmap.backend.review.entity.ReviewTagEntity;
import com.honbabmap.backend.review.entity.TagEntity;
import com.honbabmap.backend.review.repository.ReviewRepository;
import com.honbabmap.backend.review.repository.ReviewTagRepository;
import com.honbabmap.backend.review.repository.TagRepository;
import com.honbabmap.backend.user.UserEntity;
import com.honbabmap.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;

    @Transactional
    public ReviewResponse createReview(Integer restaurantId, ReviewRequest request, String loginId) {

        // 1. 유저 검증 및 조회 (UserEntity의 userId가 Integer 타입임)
        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 2. 태그 무결성 검증 (Integer 타입의 List로 받아와서 검사합니다)
        List<Integer> tagIds = request.getSelectedTagsArray();
        List<TagEntity> tags = tagRepository.findAllById(tagIds);

        // 프론트엔드가 요청한 태그 개수와 DB에서 실제로 찾은 태그 개수가 다르면 에러를 뱉어냅니다.
        if (tags.size() != tagIds.size()) {
            throw new IllegalArgumentException("유효하지 않은 태그가 포함되어 있습니다.");
        }

        // 3. 리뷰 엔티티 생성 및 저장 (모든 외래키 ID를 Integer로 연결합니다)
        ReviewEntity review = ReviewEntity.builder()
                .userId(user.getUserId()) // UserEntity의 Integer 타입 아이디
                .restaurantId(restaurantId) // 컨트롤러에서 받아온 Integer 타입의 식당 아이디
                .isBookmarked(false)
                .build();

        ReviewEntity savedReview = reviewRepository.save(review);

        // 4. 리뷰-태그 중간 테이블 저장 (객체 연관관계 매핑을 통해 조인합니다)
        for (TagEntity tag : tags) {
            ReviewTagEntity reviewTag = ReviewTagEntity.builder()
                    .review(savedReview)
                    .tag(tag)
                    .build();
            reviewTagRepository.save(reviewTag);
        }

        // 5. API 명세서 구조에 완벽하게 맞춘 응답 데이터 포장
        String createdAtStr = LocalDateTime.now().toString();

        return ReviewResponse.builder()
                .message("후기가 성공적으로 등록되었습니다.")
                .data(ReviewResponse.ReviewData.builder()
                        .reviewId(savedReview.getReviewId()) // Integer
                        .restaurantId(savedReview.getRestaurantId()) // Integer
                        .writerNickname(user.getNickname())
                        .createdAt(createdAtStr)
                        .build())
                .build();
    }
}