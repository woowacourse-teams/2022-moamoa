package com.woowacourse.moamoa.member.domain.repository.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException() {
        super("회원이 존재하지 않습니다.");
    }
}
