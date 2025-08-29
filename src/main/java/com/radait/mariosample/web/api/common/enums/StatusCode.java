package com.radait.mariosample.web.api.common.enums;

public enum StatusCode {

    ACTIVE("A", "정상"),
    DELETED("D", "삭제"),
    HIDDEN("H", "숨김");

    private final String code;
    private final String label;

    StatusCode(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() { return code; }
    public String getLabel() { return label; }

    public static StatusCode fromCode(String code) {
        for (StatusCode status : values()) {
            if (status.code.equals(code)) return status;
        }
        throw new IllegalArgumentException("유효하지 않은 코드 값: " + code);
    }


}