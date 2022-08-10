package com.woowacourse.moamoa.referenceroom.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class NotParticipatedMemberException extends BadRequestException {

    public NotParticipatedMemberException() {
        super("스터디에 참여하지 않은 회원입니다.");
    }
}
