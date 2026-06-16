package com.honbabmap.backend.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reviews_tags")
public class ReviewTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_tag_id", nullable = false)
    private Integer reviewTagId;

    @Column(name = "review_id", nullable = false)
    private Integer reviewId;

    @Column(name = "tag_id", nullable = false)
    private Integer tagId;
}
