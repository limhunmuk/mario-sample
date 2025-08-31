package com.radait.mariosample.web.exception;

public class ResourceNotFoundException extends CustomRuntimeException {

    private static final String MESSAGE = "존재하지 않는 자원입니다.";

    public ResourceNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
