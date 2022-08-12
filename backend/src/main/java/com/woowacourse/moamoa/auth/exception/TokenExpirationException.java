package com.woowacourse.moamoa.auth.exception;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;

public class TokenExpirationException extends UnauthorizedException {

    public TokenExpirationException() {
        super("만료된 토큰입니다.");
    }
}
