package com.beyond.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // SpringSecurity 설정을 customizing하기 위함
@EnableGlobalMethodSecurity(prePostEnabled = true)  // pre : 사전, post : 사후 인증 검사
public class SecurityConfig {
    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {

//        httpSecurity : SecurityFilterChain의 구현체
        return httpSecurity
                // csrf 공격에 대한 설정은 하지 않겠다는 의미
                .csrf().disable()
                .authorizeRequests()
                    // antMatchers() : 인증 제외
                    .antMatchers("/", "/author/register", "/author/login-screen")
                    .permitAll()
                    // antMatchers() 외 요청은 모두 인증 필요
                    .anyRequest().authenticated()
                .and()
                    // 만약에 세선 로그인이 아닌 토큰 로그인일 경우
                    // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                    .loginPage("/author/login-screen")
                    // doLogin 메서드는 스프링에서 미리 구현되어 있음
                    .loginProcessingUrl("/doLogin")
                        // 다만, doLogin에 넘겨줄 email, password 속성명은 별도 지정
                        .usernameParameter("email")
                        .passwordParameter("password")
                .and()
                    .logout()
                    // security에서 구현된 doLogin 기능 그대로 사용
                    .logoutUrl("/doLogout")
                .and()
                .build();
    }
}
