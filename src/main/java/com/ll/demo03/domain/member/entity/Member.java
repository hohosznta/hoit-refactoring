package com.ll.demo03.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.ll.demo03.global.base.BaseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import com.ll.demo03.domain.member.entity.AuthProvider;

import java.util.Random;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Member extends BaseEntity {
    private String name;
    private String email;
    private String profile;

    @Builder.Default
    private String nickname=generateRandomNickname();

    @Builder.Default
    private int credit = 5; //토큰 개수

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    private static String generateRandomNickname() {
        Random random = new Random();
        int randomNumber = 10000 + random.nextInt(90000); // 10000 ~ 99999
        return "user" + randomNumber;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    // 추가
    public void updateProfile(String profileImage) {
        this.profile = profileImage;
    }

    // 또는 둘 다 업데이트하는 메서드
    public void updateProfileInfo(String nickname, String profileImage) {
        this.nickname = nickname;
        if (profileImage != null && !profileImage.trim().isEmpty()) {
            this.profile = profileImage;
        }
    }
}
