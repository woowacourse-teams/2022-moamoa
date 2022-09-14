package com.woowacourse.moamoa.studyroom.domain.review.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UnwrittenReviewException extends BadRequestException {

    public UnwrittenReviewException() {
        super("내가 작성한 후기가 아닙니다.");
    }
}
