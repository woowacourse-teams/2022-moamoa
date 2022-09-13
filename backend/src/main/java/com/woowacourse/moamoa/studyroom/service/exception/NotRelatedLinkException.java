package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class NotRelatedLinkException extends BadRequestException {

    public NotRelatedLinkException() {
        super("해당 스터디에 작성된 링크 공유글이 아닙니다.");
    }
}
