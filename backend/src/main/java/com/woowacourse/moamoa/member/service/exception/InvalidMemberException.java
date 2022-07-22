package com.woowacourse.moamoa.member.service.exception;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;

public class InvalidMemberException extends UnauthorizedException {
    public InvalidMemberException(final String message) {
        super(message);
    }
}
