package com.sociallogin.entity;

import com.sociallogin.constant.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Data
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String provider;

    // SocialUser 엔티티 클래스
    @Builder
    public SocialUser(String name, String email, String picture, Role role, String provider) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.provider = provider;
    }

    // SocialUser 객체의 이름과 사진 정보를 업데이트하는 메서드
    public SocialUser update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    // SocialUser의 역할(Role)을 반환하는 메서드
    public String getRoleKey() {
        return this.getRole().getKey();
    }
}
