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
    @Column(name = "review_tag_id")
    private Long id;

    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "tag_id")
    private Long tagId;
}
