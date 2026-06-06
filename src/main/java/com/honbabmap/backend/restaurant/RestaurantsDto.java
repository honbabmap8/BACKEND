package com.honbabmap.backend.restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantsDto {
    private Long id;

    private String name;

    private String detail;

    private double lat;

    private double lng;

    private String img;

    private String menu;

    private int level;

    private double distance;

    private int stationId;
}