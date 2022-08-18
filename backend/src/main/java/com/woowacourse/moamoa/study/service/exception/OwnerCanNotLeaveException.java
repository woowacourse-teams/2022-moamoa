package com.woowacourse.moamoa.study.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class OwnerCanNotLeaveException extends BadRequestException {

    public OwnerCanNotLeaveException() {
        super("스터디장은 스터디를 탈퇴할 수 없습니다.");
    }
}
