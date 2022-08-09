package com.woowacourse.moamoa.referenceroom.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UnwrittenLinkException extends BadRequestException {

    public UnwrittenLinkException() {
        super("내가 작성한 링크 공유글이 아닙니다.");
    }
}
