package com.honbabmap.backend.restaurant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="restaurants")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rest_id") // ERD의 식당 고유 ID
    private Long id;

    @Column(name = "rest_name") // ERD의 식당 이름
    private String name;

    @Column(name = "rest_detail") // ERD의 식당 한줄소개
    private String detail;

    @Column(name = "rest_lat") // ERD의 식당 위치 위도
    private double lat;

    @Column(name = "rest_lng") // ERD의 식당 위치 경도
    private double lng;

    @Column(name = "image_url") // ERD의 대표 이미지 url
    private String img;

    @Column(name = "representative_menu") // ERD의 대표 메뉴(여러 메뉴를 하나의 문자열로 저장)
    private String menu;

    @Column(name = "rest_level") // ERD의 혼밥난이도(1~5)
    private int level;

    @Column(name = "rest_distance") // ERD의 역과의 거리
    private double distance;

    @Column(name = "station_id") // ERD의 지하철역 고유 ID
    private int stationId;

    @CreationTimestamp // INSERT 시 자동으로 현재 시간 기록
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
