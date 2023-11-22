package com.sociallogin.config;

import com.sociallogin.service.CustomOAuth2UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    // WebSecurityCustomizer를 구성합니다.
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web
                .ignoring()
                .mvcMatchers("/home")  // "/home" 경로는 보안 검사에서 제외
                .antMatchers("/h2-console/**");  // "/h2-console/**" 경로는 보안 검사에서 제외
    }

    // SecurityFilterChain을 구성합니다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()  // "/h2-console/**" 경로에 대한 권한 부여
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**").disable()  // "/h2-console/**" 경로에 대한 CSRF 비활성화
                .httpBasic()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/home2", true)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
        return httpSecurity.build();
    }
}
