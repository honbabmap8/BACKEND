// 사용자가 후기를 작성할 때 후기 태그에 대한 entity 클래스
package com.honbabmap.backend.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="tags")

public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false) // ERD의 후기태그 고유 ID
    private Integer tagId;

    @Column(name = "tag_name", nullable = false) // ERD의 후기태그 이름
    private String tagName;
}
