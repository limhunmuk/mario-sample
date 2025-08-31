package com.radait.mariosample.web.exception;

public class ForbiddenException extends CustomRuntimeException {

    private static final String MESSAGE = "접근 권한이 없습니다.";

    public ForbiddenException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 403;
    }
}
