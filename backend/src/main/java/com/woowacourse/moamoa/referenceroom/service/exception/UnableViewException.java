package com.woowacourse.moamoa.referenceroom.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UnableViewException extends BadRequestException {

    public UnableViewException() {
        super("스터디에 참여하지 않은 회원은 조회할 수 없습니다.");
    }
}
