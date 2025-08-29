package com.radait.mariosample.web.api.common.enums;

public enum YNCode {

    NULL("", "미지정"),
    Y("Y", "예"),
    N("N", "아니오");

    private final String code;
    private final String label;

    YNCode(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() { return code; }
    public String getLabel() { return label; }

    public static YNCode fromCode(String code) {
        for (YNCode status : values()) {
            if (status.code.equals(code)) return status;
        }
        throw new IllegalArgumentException("유효하지 않은 코드 값: " + code);
    }

}