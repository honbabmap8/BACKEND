package com.honbabmap.backend.eatbti;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EatBTI")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EatbtiEntity {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "score1", nullable = false)
    private int score1;

    @Column(name = "score2", nullable = false)
    private int score2;

    @Column(name = "score3", nullable = false)
    private int score3;

    @Column(name = "score4", nullable = false)
    private int score4;

    @Column(name = "score5", nullable = false)
    private int score5;

    // 3번 문항 가중치(1.5배) 적용을 위해 double 타입 사용
    @Column(name = "total", nullable = false)
    private double total;
}