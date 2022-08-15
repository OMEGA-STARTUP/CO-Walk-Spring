package com.omega.cowalk.security.configs;

import com.omega.cowalk.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] ANONYMOUS_AUTH_ENTRY_POINT = {
            "/" ,"/user/login", "/user/register/**", "/user/id-inquiry/**", "/user/pw-inquiry/**"
    };

    private final AuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler jwtAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // JWT TOKEN 인증 방식을 위한 비활성화
        http.formLogin().disable();
        http.httpBasic().disable();
        http.csrf().disable();
        // 세션 미사용
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //권한 설정
        http.authorizeRequests()
                .antMatchers(ANONYMOUS_AUTH_ENTRY_POINT).anonymous()
                .antMatchers("/admin/**").hasRole(Role.ADMIN.toString())
                .anyRequest().authenticated();

        // Jwt 인증 Custom DSL
        customConfigurer(http);

        return http.build();
    }

    // 정적 리소스 접근제한
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    private void customConfigurer(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.apply(new JwtAuthenticationFilterConfigurer<>())
                .setAuthenticationManager(authenticationManager)
                .successHandlerJwt(jwtAuthenticationSuccessHandler)
                .failureHandlerJwt(jwtAuthenticationFailureHandler)
                .loginProcessingUrl("/user/login");
    }
}
