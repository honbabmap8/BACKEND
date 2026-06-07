// 식당들의 특징에 대한 entity 클래스
package com.honbabmap.backend.restaurant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="features")
public class FeatureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id") // ERD의 후기태그 고유 ID
    private Long id;

    @Column(name = "feature_name") // ERD의 후기태그 이름
    private String name;
}
