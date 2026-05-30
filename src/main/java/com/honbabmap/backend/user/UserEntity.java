package com.honbabmap.backend.user;

import jakarta.persistence.*;
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
    private Long id; // Auto Increment를 위해 Long 타입 사용

    @Column(name = "login_id", nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(name = "login_pw", nullable = false)
    private String password; // 암호화된 문자열 저장을 위해 String 선언

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(name = "user_level", nullable = false)
    private Integer honbabLevel;

    @CreationTimestamp // INSERT 시 자동으로 현재 시간 기록
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}