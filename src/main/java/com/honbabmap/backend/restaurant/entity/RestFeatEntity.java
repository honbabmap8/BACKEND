package com.honbabmap.backend.restaurant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="rest_feat")
public class RestFeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rest_feat_id")
    private Long restFeatId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id")
    private RestaurantEntity restaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feat_id")
    private FeatureEntity featureId;
}
