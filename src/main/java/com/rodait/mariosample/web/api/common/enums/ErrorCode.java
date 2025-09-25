package com.rodait.mariosample.web.api.common.enums;

public enum ErrorCode {

    // 400 계열
    INVALID_REQUEST(400, "잘못된 요청입니다."),
    VALIDATION_FAILED(422, "입력값 검증에 실패했습니다."),
    DUPLICATE_RESOURCE(409, "이미 존재하는 자원입니다."),
    ACCOUNT_ALREADY_EXISTS(409, "이미 가입된 아이디입니다."),

    // 인증/인가
    UNAUTHORIZED(401, "인증이 필요합니다."),
    FORBIDDEN(403, "접근 권한이 없습니다."),

    // 조회 실패
    NOT_FOUND(404, "요청한 자원을 찾을 수 없습니다."),

    // 서버/외부 연동
    INTERNAL_ERROR(500, "서버 내부 오류가 발생했습니다."),
    SERVICE_UNAVAILABLE(503, "현재 서비스를 이용할 수 없습니다.");

    private final int status;
    private final String defaultMessage;

    ErrorCode(int status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }
    public int getStatus() { return status; }
    public String getDefaultMessage() { return defaultMessage; }
}
