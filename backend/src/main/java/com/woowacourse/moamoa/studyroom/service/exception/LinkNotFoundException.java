package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class LinkNotFoundException extends NotFoundException {

    public LinkNotFoundException() {
        super("링크 공유글을 찾을 수 없습니다.");
    }
}
