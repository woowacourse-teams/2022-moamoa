package com.woowacourse.moamoa.auth.service.oauthclient.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(final String message) {
        super(message);
    }
}
