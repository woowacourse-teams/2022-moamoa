package com.woowacourse.moamoa.study.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class InvalidUpdatingException  extends BadRequestException {

    public InvalidUpdatingException() {
        super("스터디 수정이 불가능합니다.");
    }
}
