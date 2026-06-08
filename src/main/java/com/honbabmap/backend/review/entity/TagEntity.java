// 사용자가 후기를 작성할 때 후기 태그에 대한 entity 클래스
package com.honbabmap.backend.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tags")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id") // ERD의 후기태그 고유 ID
    private Long id;

    @Column(name = "tag_name") // ERD의 후기태그 이름
    private String name;
}
