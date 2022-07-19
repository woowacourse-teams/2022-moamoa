package com.woowacourse.moamoa.auth.service.oauthclient.exception;

public class InvalidMemberException extends UnauthorizedException {
    public InvalidMemberException(final String message) {
        super(message);
    }
}
