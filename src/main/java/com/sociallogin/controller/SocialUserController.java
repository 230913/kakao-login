package com.sociallogin.controller;

import com.sociallogin.session.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SocialUserController {

    private final HttpSession httpSession;

    // "/home" 경로로 GET 요청을 처리하는 메서드
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        // "testString" 속성을 모델에 추가
        model.addAttribute("testString", "this is from controller");
        return "home"; // "home" 뷰 페이지를 렌더링
    }

    // "/home2" 경로로 GET 요청을 처리하는 메서드
    @RequestMapping(value = "/home2", method = RequestMethod.GET)
    public String home2(Model model) {
        // HttpSession에서 "user" 속성을 가져와 SessionUser 객체로 캐스팅
        SessionUser socialUser = (SessionUser) httpSession.getAttribute("user");

        if(socialUser != null) {
            // 모델에 사용자 이름 및 사진 속성을 추가
            model.addAttribute("userName", socialUser.getName());
            model.addAttribute("picture", socialUser.getPicture());
        }

        return "home"; // "home" 뷰 페이지를 렌더링
    }
}



//    private final HttpSession httpSession;
//
//    @GetMapping("/")
//    public String index(Model model){
//        model.addAttribute("posts", postsService.findAllDesc());
//
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//
//        if(user != null){
//            model.addAttribute("userName", user.getName());
//        }
//        return "index";
//    }

//    @GetMapping("/login/oauth2/code/kakao")
//    public String kakaoCallback(@RequestParam String code, Model model){
////        model.addAttribute("posts", postsService.findAllDesc());
//
//        System.out.println("======>>> " + code);
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//
//        if(user != null){
//            model.addAttribute("userName", user.getName());
//        }
//
//
//        return "redirect:/";
//    }
//}
