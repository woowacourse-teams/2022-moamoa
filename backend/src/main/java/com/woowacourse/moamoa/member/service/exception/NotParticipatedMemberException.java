package com.woowacourse.moamoa.member.service.exception;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;

public class NotParticipatedMemberException extends UnauthorizedException {

    public NotParticipatedMemberException() {
        super("스터디에 참여한 회원만 후기를 작성할 수 있습니다.");
    }
}
