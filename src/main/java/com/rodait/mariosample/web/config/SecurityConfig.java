package com.rodait.mariosample.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodait.mariosample.web.api.member.mapper.MemberMapper;
import com.rodait.mariosample.web.filter.JwtAuthenticationFilter;
import com.rodait.mariosample.web.filter.JwtAuthorizationFilter;
import com.rodait.mariosample.web.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final MemberMapper userRepository;
    private final MemberDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                    AuthenticationManager authenticationManager
                                                   ) throws Exception {

        // JwtAuthorizationFilter는 모든 요청에서 토큰을 검증하기 위해 추가됩니다.
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtTokenUtil);

        // 로그인 필터는 JwtAuthenticationFilter에서 처리합니다.
        JwtAuthenticationFilter jwtAuthenticationFilter =
                new JwtAuthenticationFilter(authenticationManager, jwtTokenUtil, userRepository, objectMapper,"/api/login");

//        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(userRepository));

        return http
                .authorizeHttpRequests(

                        auth -> auth
                                //.anyRequest().permitAll() // 모든 요청에 대해 인증을 요구하지 않음
                                //auth.requestMatchers("/api/login").permitAll()
                                //.requestMatchers("/api/signup").authenticated()
                                //auth.anyRequest().permitAll() // 모든 요청에 대해 인증을 요구하지 않음
                                .requestMatchers("/", "/api/login", "/api/logout", "/api/main", "/docs/**").permitAll() // 로그인 요청은 인증 필요 없음
                                .requestMatchers((HttpMethod.POST), "/api/members").permitAll()
                                .requestMatchers((HttpMethod.GET), "/api/post/**", "/api/notice/**").permitAll() // 게시글 조회는 인증 필요 없음
                                //.requestMatchers((HttpMethod.POST), "/api/post/**", "/api/notice/**").authenticated() // 게시글 작성은 인증 필요
                                //.requestMatchers((HttpMethod.PUT), "/api/post/**", "/api/notice/**").authenticated() // 게시글 수정은 인증 필요
                                //.requestMatchers((HttpMethod.DELETE), "/api/post/**", "/api/notice/**").authenticated() // 게시글 삭제는 인증 필요
                                //.requestMatchers("/api/member/**").authenticated() // 회원 관련 요청은 인증 필요
                                //.requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자 요청은 ADMIN 권한 필요'
                                .anyRequest().authenticated() // 다른 모든 요청은 인증 필요

                             // 다른 모든 요청은 인증 필요
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 로그인 필터 먼저 추가
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
                .rememberMe(rm -> rm.rememberMeParameter("remember-me")
                        .alwaysRemember(false)
                        .tokenValiditySeconds(60*5)
                )
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); // 필요한 경우에만 세션 생성
                    session.maximumSessions(1) // 사용자당 세션 하나로 제한
                            .maxSessionsPreventsLogin(true); // 세션 초과 시 로그인 차단
                })
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            MemberMapper userRepository,
            ObjectMapper objectMapper
    ) {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenUtil, userRepository, objectMapper, "/api/login");
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return jwtAuthenticationFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // vue js 설정시 추가해줘야 됨
    // https://subbak2.com/11
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                // 원하는 URL들
                //"swagger-ui.html",      // Swagger 사용 시
                "/index.html",          // Front-end에서 빌드된 static 파일
                "/favicon.ico",         // 아이콘 파일
                "/css/**",              // CSS 파일
                "/fonts/**",            // 폰트 파일
                "/img/**",              // 이미지 파일
                "/js/**",                // JavaScript 파일
                "/assets/**",           // 기타 자원 파일
                "/docs/**"              // spring rest docs

        );
    }
}
