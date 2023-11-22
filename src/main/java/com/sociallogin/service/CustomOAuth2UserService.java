package com.sociallogin.service;

import com.sociallogin.dto.OAuthAttributes;
import com.sociallogin.entity.SocialUser;
import com.sociallogin.repository.SocialUserRepository;
import com.sociallogin.session.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final SocialUserRepository socialUserRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2UserRequest를 처리할 디폴트 서비스 인스턴스 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        // OAuth2UserRequest를 이용해 OAuth2User 정보를 가져옴
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 사용자 정보를 얻기 위해 사용하는 OAuth2 공급자 이름
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 사용자 정보에서 이름 속성을 가져오기 위한 속성 이름
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuthAttributes 객체를 생성하여 사용자 정보를 매핑
        OAuthAttributes attributes = OAuthAttributes.of(provider, userNameAttributeName, oAuth2User.getAttributes());

        // 사용자 정보를 저장하거나 업데이트
        SocialUser socialUser = saveOrUpdate(attributes);

        // 세션에 사용자 정보 저장
        httpSession.setAttribute("user", new SessionUser(socialUser));

        // Spring Security에 사용될 OAuth2User 객체 반환
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(socialUser.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private SocialUser saveOrUpdate(OAuthAttributes attributes) {
        // 사용자 정보를 찾아서 업데이트하거나 저장
        SocialUser socialUser = socialUserRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        // 사용자 정보를 데이터베이스에 저장
        return socialUserRepository.save(socialUser);
    }
}





// 이 메서드는 OAuth2 인증 중 사용자 정보를 로드하는 역할을 합니다.
// 생성자 및 필요한 의존성 주입

//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
////        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//
//        // DefaultOAuth2UserService에 위임하여 사용자 정보를 로드합니다.
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//        // userRequest에서 registrationId (OAuth2 서비스 공급자 식별자)와 userNameAttributeName (사용자 식별 필드)를 가져옵니다.
//
//        // OAuth2 서비스 id (카카오)
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        // OAuthAttributes 클래스를 사용하여 사용자 속성을 추출합니다
//        // OAuth2UserService
//        OAuthAttributes attribustes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//        // 사용자를 저장 또는 업데이트하고 세션에 저장합니다.
//
//        Member member = saveOrUpdate(attributes);
//        httpSession.setAttribute("user", new SessionUser(member)); // SessionUser (직렬화된 dto 클래스 사용)
//
//        // 카카오 사용자 정보 가져오기
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        String email = (String) attributes.get("email");
//        String nickname = (String) attributes.get("nickname");
//
//
//        // 사용자의 권한, 속성 및 이름 속성 키를 가진 DefaultOAuth2User를 반환합니다.
//        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey());
//
//
//    }
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        // DefaultOAuth2UserService에 위임하여 사용자 정보를 로드합니다.
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//        // userRequest에서 registrationId (OAuth2 서비스 공급자 식별자)와 userNameAttributeName (사용자 식별 필드)를 가져옵니다.
//
//        // OAuth2 서비스 id (카카오)
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        // OAuthAttributes 클래스를 사용하여 사용자 속성을 추출합니다
//        // OAuth2UserService
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//        // 사용자를 저장 또는 업데이트하고 세션에 저장합니다.
//        Member member = saveOrUpdate(attributes);
//        httpSession.setAttribute("user", new SessionUser(member)); // SessionUser (직렬화된 dto 클래스 사용)
//
//
//        // 사용자의 권한, 속성 및 이름 속성 키를 가진 DefaultOAuth2User를 반환합니다.
//        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey());
//    }
//
//
//
//    // 사용자를 생성 또는 업데이트하는 서비스 로직입니다.
//    private SocialUser saveOrUpdate(OAuthAttributes attributes) {
//        SocialUser socialUser = memberRepository.findByEmail(attributes.getEmail())
//                .map(entity -> entity.update(attributes.getName(), attributes.getImage()))
//                .orElse(attributes.toEntity());
//        return memberRepository.save(socialUser);
//    }
//
//
//}
