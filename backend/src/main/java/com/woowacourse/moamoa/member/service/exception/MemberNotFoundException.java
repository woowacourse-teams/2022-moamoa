package com.woowacourse.moamoa.member.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException() {
        super("회원을 찾을 수 없습니다.");
    }
}
