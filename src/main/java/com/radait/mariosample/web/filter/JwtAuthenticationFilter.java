package com.radait.mariosample.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radait.mariosample.web.api.member.dto.LoginDetailDto;
import com.radait.mariosample.web.api.member.dto.MemberDetailDto;
import com.radait.mariosample.web.api.member.dto.MemberSearchDto;
import com.radait.mariosample.web.api.member.entity.Member;
import com.radait.mariosample.web.api.member.mapper.MemberMapper;
import com.radait.mariosample.web.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final MemberMapper memberMapper;
    private final ObjectMapper objectMapper;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtTokenUtil jwtTokenUtil,
                                   MemberMapper memberMapper,
                                   ObjectMapper objectMapper,
                                   String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        this.authenticationManager = authenticationManager;
        this.memberMapper = memberMapper;
        this.jwtTokenUtil = jwtTokenUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {

            LoginDetailDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDetailDto.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getLoginId(), loginDto.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String username = authResult.getName();
        String accessToken = jwtTokenUtil.createToken(username);
        String refreshToken = jwtTokenUtil.createRefreshToken(username);

        MemberSearchDto condition = new MemberSearchDto();
        condition.setLoginId(username);
        condition.setUsername(username);

        Member mem = memberMapper.selectMemberLogin(username);
        if (mem == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        MemberDetailDto member = MemberDetailDto.of(mem);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        record LoginResponseDto(String accessToken,String refreshToken,MemberDetailDto member){}

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        LoginResponseDto body = new LoginResponseDto(accessToken,refreshToken,member);
        objectMapper.writeValue(response.getWriter(), body);
    }

}
