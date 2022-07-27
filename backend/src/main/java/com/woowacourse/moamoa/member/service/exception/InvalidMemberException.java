package com.woowacourse.moamoa.member.service.exception;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;

public class InvalidMemberException extends UnauthorizedException {

    public InvalidMemberException() {
        super("회원을 찾을 수 없습니다.");
    }
}
