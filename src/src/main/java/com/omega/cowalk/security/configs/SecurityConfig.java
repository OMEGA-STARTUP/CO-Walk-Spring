package com.omega.cowalk.security.configs;

import com.omega.cowalk.domain.entity.user.Role;
import com.omega.cowalk.repository.UserRepository;
import com.omega.cowalk.security.filter.IgnorePathFilterRules;
import com.omega.cowalk.security.filter.JwtAuthorizationExceptionFilter;
import com.omega.cowalk.security.filter.JwtAuthorizationFilter;
import com.omega.cowalk.security.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] ANONYMOUS_AUTH_ENTRY_POINT = {
            "/token/re-issue", "/user/login", "/user/register/**", "/user/id-inquiry/**", "/user/pw-inquiry/**", "/user/password/**"
    };

    private final AuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final IgnorePathFilterRules ignorePathFilterUtil;

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
                .antMatchers("/admin/**").hasRole(Role.ADMIN.toString())
                .antMatchers(ANONYMOUS_AUTH_ENTRY_POINT).permitAll()
                .anyRequest().authenticated();

        // Jwt 인증 Custom DSL
        customConfigurer(http);
        http.apply(new CustomDsl());

        return http.build();
    }


    public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity>{
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilterBefore(new JwtAuthorizationFilter(authenticationManager, userRepository, tokenService, ignorePathFilterUtil), UsernamePasswordAuthenticationFilter.class);
            http.addFilterBefore(new JwtAuthorizationExceptionFilter(), JwtAuthorizationFilter.class);
        }
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
