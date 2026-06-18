package com.honbabmap.backend.eatbti;

import com.honbabmap.backend.eatbti.dto.EatbtiRequest;
import com.honbabmap.backend.eatbti.dto.EatbtiResponse;
import com.honbabmap.backend.user.UserEntity;
import com.honbabmap.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.h2.engine.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EatbtiService {

    private final EatbtiRepository eatbtiRepository;
    private final UserRepository userRepository;

    // 1. EatBTI 검사 결과 계산 및 저장 (POST)
    @Transactional
    public EatbtiResponse.Submit submitEatbti(String loginId, List<Integer> answers) {

        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        int score1 = 6 - answers.get(0);
        int score2 = 6 - answers.get(1);
        int score3 = 6 - answers.get(2);
        int score4 = 6 - answers.get(3);
        int score5 = 6 - answers.get(4);

        double totalScore = score1 + score2 + (score3 * 1.5) + score4 + score5;
        int honbabLevel = determineLevel(totalScore);

        EatbtiEntity eatbtiEntity = EatbtiEntity.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .score1(score1)
                .score2(score2)
                .score3(score3)
                .score4(score4)
                .score5(score5)
                .total(totalScore)
                .build();
        eatbtiRepository.save(eatbtiEntity);

        // 유저 레벨 업데이트

        user.updateHonbabLevel(honbabLevel);

        return EatbtiResponse.Submit.builder()
                .userId(user.getUserId())
                .honbabLevel(honbabLevel)
                .build();
    }

    // 2. EatBTI 검사 결과 조회 (GET)
    @Transactional(readOnly = true)
    public EatbtiResponse.Result getEatbtiResult(String loginId) {

        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        EatbtiEntity eatbti = eatbtiRepository.findByLoginId(user.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아직 EatBTI 검사를 진행하지 않은 사용자입니다."));

        Integer honbabLevel = user.getHonbabLevel();

        return EatbtiResponse.Result.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .honbabLevel(honbabLevel)
                .levelName(getLevelName(honbabLevel))
                .description(getDescription(honbabLevel))
                .imageUrl(getImageUrl(honbabLevel))
                .build();
    }

    private int determineLevel(double totalScore) {
        if (totalScore >= 24.0) return 5;
        else if (totalScore >= 19.0) return 4;
        else if (totalScore >= 14.0) return 3;
        else if (totalScore >= 9.0) return 2;
        else return 1;
    }

    private String getLevelName(int level) {
        return switch (level) {
            case 5 -> "혼밥 마스터";
            case 4 -> "혼밥 고수";
            case 3 -> "혼밥 즐기는 편";
            case 2 -> "혼밥 익숙해지는 중";
            default -> "혼밥 초보자";
        };
    }

    private String getDescription(int level) {
        return switch (level) {
            case 5 -> "어떤 식당이든 혼자서 당당하게 즐길 수 있는 마스터입니다!";
            case 4 -> "대부분의 식당에서 여유롭게 혼밥을 즐기실 수 있습니다.";
            case 3 -> "혼밥이 낯설지 않으며 편안하게 즐기기 시작한 단계입니다.";
            case 2 -> "국밥집이나 패스트푸드점에서는 쉽게 혼밥을 하실 수 있습니다.";
            default -> "아직은 혼자 밥 먹는 것이 조금 어색하고 부끄러운 단계입니다.";
        };
    }

    private String getImageUrl(int level) {
        return "https://example.com/images/level" + level + "_character.png";
    }
}