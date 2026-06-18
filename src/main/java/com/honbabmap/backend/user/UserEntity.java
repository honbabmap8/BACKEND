package com.honbabmap.backend.user;

import com.honbabmap.backend.eatbti.EatbtiEntity;
import com.honbabmap.backend.review.entity.TagEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // ERD의 고객 고유 ID
    private Integer userId;

    @Column(name = "login_id", nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(name = "login_pw", nullable = false)
    @NotBlank
    private String password; // 암호화된 문자열 저장을 위해 String 선언

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(name = "user_level", nullable = false)
    private Integer honbabLevel;

    public void updateHonbabLevel(Integer honbabLevel){
        this.honbabLevel = honbabLevel; }

    // 조인 추가: UserEntity와 일대일(1:1) 관계로 연결
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private EatbtiEntity eatbti;

    @CreationTimestamp // INSERT 시 자동으로 현재 시간 기록
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}