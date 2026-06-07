package com.honbabmap.backend.restaurant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="subway_stations")
public class StationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id") // ERD의 식당 고유 ID
    private Long id;

    @Column(name = "station_name") // ERD의 지하철역 이름
    private String name;

    @Column(name = "rest_lat") // ERD의 지하철 위치 위도
    private double lat;

    @Column(name = "rest_lng") // ERD의 지하철 위치 경도
    private double lng;
}