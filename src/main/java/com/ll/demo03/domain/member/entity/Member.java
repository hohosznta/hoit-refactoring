package com.ll.demo03.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.ll.demo03.global.base.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Member extends BaseEntity {
    private String name; //이름
    private String account; //아이디
    private String email; //이메일
    private String profile; //프로필 사진
    @Builder.Default
    private int token =5 ; //토큰 개수

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Role> role = new HashSet<>();
}
