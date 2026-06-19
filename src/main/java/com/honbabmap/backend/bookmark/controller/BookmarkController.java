package com.honbabmap.backend.bookmark.controller;

import com.honbabmap.backend.bookmark.dto.BookmarkResponse;
import com.honbabmap.backend.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 1. 단골가게(찜) 등록
    @PostMapping("/restaurants/{restaurantId}/bookmarks")
    public ResponseEntity<BookmarkResponse> addBookmark(
            @PathVariable Integer restaurantId,
            @AuthenticationPrincipal UserDetails userDetails) {

        // userDetails.getUsername() 대신 데이터베이스에 있는 실제 아이디를 강제로 입력
        // String testLoginId = "jaehyeok";

        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }
        String loginId = userDetails.getUsername();

        BookmarkResponse response = bookmarkService.addBookmark(restaurantId, loginId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. 단골가게(찜) 취소
    @DeleteMapping("/restaurants/{restaurantId}/bookmarks")
    public ResponseEntity<BookmarkResponse> removeBookmark(
            @PathVariable Integer restaurantId,
            @AuthenticationPrincipal UserDetails userDetails) {

        // userDetails.getUsername() 대신 데이터베이스에 있는 실제 아이디를 강제로 입력
        // String testLoginId = "jaehyeok";

        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }
        String loginId = userDetails.getUsername();

        BookmarkResponse response = bookmarkService.removeBookmark(restaurantId, loginId);
        return ResponseEntity.ok(response);
    }

    // 3. 내 단골가게 목록 조회
    @GetMapping("/users/me/bookmarks")
    public ResponseEntity<?> getMyBookmarks(
            @AuthenticationPrincipal UserDetails userDetails) {

        // 여기도 마찬가지로 userDetails.getUsername() 대신 테스트용 아이디를 넣음
        // String testLoginId = "jaehyeok";

        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }
        String loginId = userDetails.getUsername();

        // 목록 조회는 data 필드가 포함된 응답이므로 서비스에서 적절한 DTO를 반환
        return ResponseEntity.ok(bookmarkService.getMyBookmarks(loginId));
    }
}