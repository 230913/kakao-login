package com.sociallogin.session;

import com.sociallogin.entity.SocialUser;
import lombok.Getter;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;
    private String provider;

    // SessionUser 클래스의 생성자
    public SessionUser(SocialUser socialUser) {
        // SocialUser 객체로부터 사용자 이름, 이메일, 프로필 사진 및 제공자 정보를 가져와 초기화
        this.name = socialUser.getName();
        this.email = socialUser.getEmail();
        this.picture = socialUser.getPicture();
        this.provider = socialUser.getProvider();
    }
}







//import com.momento.entity.Member;
//import lombok.Getter;
//
//import java.io.Serializable;
//
//
///**
// * 직렬화 기능을 가진 User클래스
// */
//@Getter
//public class SessionUser implements Serializable {
//    private String name;
//    private String email;
//    private String image;
//    public SessionUser(Member member){
//        this.name = member.getName();
//        this.email = member.getEmail();
//        this.image = member.getImage();
//    }
//}