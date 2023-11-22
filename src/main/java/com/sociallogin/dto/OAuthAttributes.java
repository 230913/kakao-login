package com.sociallogin.dto;

import com.sociallogin.constant.Role;
import com.sociallogin.entity.SocialUser;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String provider;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String provider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
    }

    // OAuth2 공급자(registrationId), 사용자 이름 속성(userNameAttributeName), 사용자 정보(attributes)를 전달받아
    // Kakao OAuthAttributes 객체를 생성하는 정적 팩토리 메서드
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofKakao(userNameAttributeName, attributes, registrationId);
    }

    // Kakao OAuth2 공급자에서 사용자 정보를 추출하여 OAuthAttributes 객체를 생성
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes, String provider) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        // OAuthAttributes 객체 생성 및 속성 값 설정
        return OAuthAttributes.builder()
                .name((String) response.get("nickname"))
                .email((String) account.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(attributes)
                .provider(provider.toUpperCase())
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // OAuthAttributes 객체를 SocialUser 엔티티로 변환
    public SocialUser toEntity() {
        return SocialUser.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .provider(provider)
                .role(Role.USER)
                .build();
    }
}











//import com.momento.constant.Role;
//import com.momento.entity.Member;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Map;
//
//@Getter
//public class OAuthAttributes {
//    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
//    private String nameAttributeKey;
//    private String name;
//    private String email;
//    private String image;
//
//    @Builder
//    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String image) {
//        this.attributes = attributes;
//        this.nameAttributeKey = nameAttributeKey;
//        this.name = name;
//        this.email = email;
//        this.image = image;
//    }
//
//
//    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
//        //(new!) kakao
//       "kakao".equals(registrationId);
//
//       return ofKakao("id", attributes);
//
//
//    }
//
//    // (new!)
//    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
//        // kakao는 kakao_account에 유저정보가 있다. (email)
//        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
//        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
//        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
//
//        return OAuthAttributes.builder()
//                .name((String) kakaoProfile.get("nickname"))
//                .email((String) kakaoAccount.get("email"))
//                .image((String) kakaoProfile.get("profile_image_url"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    public Member toEntity() {
//        var member = new Member();
//        member.setName(name);
//        member.setEmail(email);
//        member.setImage(image);
//        member.setRole(Role.USER);
//        return member;
//    }
//
//}

