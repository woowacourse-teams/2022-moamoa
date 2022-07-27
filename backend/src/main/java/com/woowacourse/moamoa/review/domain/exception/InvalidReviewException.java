package com.woowacourse.moamoa.review.domain.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class InvalidReviewException extends BadRequestException {

    private static final String message = "스터디 시작 전 후기를 작성할 수 없습니다.";

    public InvalidReviewException() {
        super(message);
    }

    public InvalidReviewException(final String message) {
        super(message);
    }
}
