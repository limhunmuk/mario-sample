package com.radait.mariosample.web.util;

import jakarta.servlet.http.HttpServletRequest;

public class IPUtils {
    /**
     * 클라이언트 IP 주소를 가져오는 메서드
     *
     * @param request HttpServletRequest 객체
     * @return 클라이언트 IP 주소
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For"); // 프록시 서버 사용 시
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}