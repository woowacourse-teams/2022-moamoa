package com.woowacourse.moamoa.study.domain.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class ParticipantCanNotKickOutException extends BadRequestException {

    public ParticipantCanNotKickOutException() {
        super("방장만 스터디원을 강퇴시킬 수 있습니다.");
    }
}
