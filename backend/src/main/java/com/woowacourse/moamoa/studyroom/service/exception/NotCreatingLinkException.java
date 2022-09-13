package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class NotCreatingLinkException extends BadRequestException {

    public NotCreatingLinkException() {
        super("링크 공유를 할 수 없습니다.");
    }
}
