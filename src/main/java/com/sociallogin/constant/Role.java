package com.sociallogin.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER", "손님"),   // USER 역할을 정의하며, "ROLE_USER"와 "손님"으로 초기화합니다.
    ADMIN("ROLE_ADMIN", "관리자");  // ADMIN 역할을 정의하며, "ROLE_ADMIN"와 "관리자"로 초기화합니다.

    private final String key;    // 역할의 키
    private final String title;  // 역할의 제목
}
