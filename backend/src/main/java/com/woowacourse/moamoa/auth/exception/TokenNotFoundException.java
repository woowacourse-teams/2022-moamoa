package com.woowacourse.moamoa.auth.exception;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;

public class TokenNotFoundException extends UnauthorizedException {

    public TokenNotFoundException() {
        super("토큰이 존재하지 않습니다.");
    }
}
