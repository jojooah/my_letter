package com.jojo.my_letter.config;

import com.jojo.my_letter.config.security.CustomLogoutSuccessHandler;
import com.jojo.my_letter.config.security.LoginFailHandler;
import com.jojo.my_letter.config.security.LoginSuccessHandler;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 시큐리티필터가 스프링 필터체인에 등록된다
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final LoginFailHandler loginFailHandler;
    // private final CustomAuthenticationProvider customAuthenticationProvider; // 여기다가 넣으니까 순환참조남...ㅜㅜ

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/login", "/error", "/join", "/index").permitAll()
                        .requestMatchers("/public-api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin //로그인페이지로 보낸다
                        .loginPage("/login")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .successHandler(loginSuccessHandler)
                        .defaultSuccessUrl("/newsletter/item")
                        .failureHandler(loginFailHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler) // 커스텀 로그아웃 성공 핸들러 설정
                        .invalidateHttpSession(true)
                        .deleteCookies("SESSION") //todo. 이걸 하는데도 쿠키가 안지워진다.
                        .logoutSuccessUrl("/login")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http));

        return http.build();
    }

    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/assets/**"
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
