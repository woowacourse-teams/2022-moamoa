package com.woowacourse.moamoa.auth.exception;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;

public class RefreshTokenExpirationException extends UnauthorizedException {
    public RefreshTokenExpirationException() {
        super("만료된 리프래시 토큰입니다.");
    }
}
