package com.radait.mariosample.web.exception;

public class AccountAlreadyExistsRuntimeException extends CustomRuntimeException {

    private static final String MESSAGE = "이미 가입된 아이디입니다.";

    public AccountAlreadyExistsRuntimeException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
