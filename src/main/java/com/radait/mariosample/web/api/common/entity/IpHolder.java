package com.radait.mariosample.web.api.common.entity;

public class IpHolder {

    private static final ThreadLocal<String> ipHolder = new ThreadLocal<>();

    public static void setIp(String ip) {
        ipHolder.set(ip);
    }

    public static String getIp() {
        return ipHolder.get();
    }

    public static void clear() {
        ipHolder.remove();
    }
}
