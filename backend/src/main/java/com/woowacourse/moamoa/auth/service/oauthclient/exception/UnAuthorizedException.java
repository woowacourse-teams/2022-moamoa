package com.woowacourse.moamoa.auth.service.oauthclient.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(final String message) {
        super(message);
    }
}
