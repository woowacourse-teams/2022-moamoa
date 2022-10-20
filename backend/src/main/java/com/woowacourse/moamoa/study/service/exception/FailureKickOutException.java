package com.woowacourse.moamoa.study.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class FailureKickOutException extends BadRequestException {

    public FailureKickOutException() {
        super("방장만 스터디원을 강퇴시킬 수 있습니다.");
    }
}
