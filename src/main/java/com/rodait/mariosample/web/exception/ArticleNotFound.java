package com.rodait.mariosample.web.exception;

/**
 * status -> 404
 */
public class ArticleNotFound extends CustomRuntimeException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public ArticleNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
