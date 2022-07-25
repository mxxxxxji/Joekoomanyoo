package com.project.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
// spring security 설정 클래스임을 알려준다
@EnableWebSecurity
// customUserDetailsService 생성자 주입을 위한 lombok
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 초기에 나오는 인증화면 풀기
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable();
        http
                .headers()
                .frameOptions()
                .disable();
    }
}