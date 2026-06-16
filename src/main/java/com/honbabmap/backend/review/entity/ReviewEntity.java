package com.honbabmap.backend.review.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

@Table(name="reviews")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Integer reviewId;

    @Column(name = "user_id", nullable = false)
    private String loginId;

    @Column(name = "rest_id", nullable = false)
    private String restaurantId;

    @Column(name = "isBookmarked", nullable = false)
    private boolean isBookmarked;

    // API 응답 명세서에 맞춘 생성 시간 필드 추가 (자동 기록)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
