package com.honbabmap.backend.bookmark.service;

import com.honbabmap.backend.bookmark.dto.BookmarkListResponse;
import com.honbabmap.backend.bookmark.dto.BookmarkListData;
import com.honbabmap.backend.bookmark.dto.RestaurantSummaryDto;
import com.honbabmap.backend.bookmark.dto.BookmarkResponse;
import com.honbabmap.backend.bookmark.entity.BookmarkEntity;
import com.honbabmap.backend.bookmark.repository.BookmarkRepository;
import com.honbabmap.backend.restaurant.entity.RestaurantEntity;
import com.honbabmap.backend.restaurant.repository.RestaurantRepository;
import com.honbabmap.backend.user.UserEntity;
import com.honbabmap.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public BookmarkResponse addBookmark(Integer restaurantId, String loginId) {
        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("식당을 찾을 수 없습니다."));

        if (bookmarkRepository.existsByUserAndRestaurant(user, restaurant)) {
            throw new RuntimeException("이미 찜한 식당입니다.");
        }

        BookmarkEntity bookmark = BookmarkEntity.builder()
                .user(user)
                .restaurant(restaurant)
                .build();

        bookmarkRepository.save(bookmark);
        return new BookmarkResponse("단골가게로 등록되었습니다.");
    }

    @Transactional
    public BookmarkResponse removeBookmark(Integer restaurantId, String loginId) {
        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("식당을 찾을 수 없습니다."));

        bookmarkRepository.deleteByUserAndRestaurant(user, restaurant);
        return new BookmarkResponse("단골가게 등록이 취소되었습니다.");
    }

    public BookmarkListResponse getMyBookmarks(String loginId, Pageable pageable) {
        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Page<BookmarkEntity> bookmarkPage = bookmarkRepository.findByUser(user, pageable);

        List<RestaurantSummaryDto> restaurantList = bookmarkPage.getContent().stream()
                .map(bookmark -> {
                    RestaurantEntity restaurant = bookmark.getRestaurant();
                    return new RestaurantSummaryDto(
                            restaurant.getRestaurantId(),
                            restaurant.getRestaurantName(),
                            restaurant.getImg(),
                            restaurant.getMenu(),
                            restaurant.getRestSoloLevel()
                    );
                })
                .toList();

        BookmarkListData data = new BookmarkListData(
                bookmarkPage.getTotalElements(),
                bookmarkPage.getTotalPages(),
                bookmarkPage.getNumber(),
                restaurantList
        );

        return new BookmarkListResponse("단골가게 목록을 성공적으로 불러왔습니다.", data);
    }
}