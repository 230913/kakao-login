package com.sociallogin.repository;

import com.sociallogin.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// SocialUser 엔티티를 관리하는 JPA Repository 인터페이스
public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
    // 이메일을 기반으로 SocialUser 엔티티를 찾는 메서드
    Optional<SocialUser> findByEmail(String email);
}
