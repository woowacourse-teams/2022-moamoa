package com.woowacourse.moamoa.review.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class ReviewNotFoundException extends NotFoundException {

    public ReviewNotFoundException() {
        super("후기를 찾을 수 없습니다.");
    }
}
