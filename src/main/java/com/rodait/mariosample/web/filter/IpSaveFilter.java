package com.rodait.mariosample.web.filter;

import com.rodait.mariosample.web.api.common.entity.IpHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * IP 저장 필터
 * 요청의 IP를 저장하는 필터입니다.
 * 이 필터는 요청이 들어올 때마다 실행되어 IP 정보를 처리합니다.
 */

@Component
public class IpSaveFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        try {
            String clientIp = getClientIp(request);
            IpHolder.setIp(clientIp);
            filterChain.doFilter(request, response);
        } finally {
            IpHolder.clear(); // 요청 끝나면 반드시 제거
        }

    }

    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null) {
            return forwarded.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
