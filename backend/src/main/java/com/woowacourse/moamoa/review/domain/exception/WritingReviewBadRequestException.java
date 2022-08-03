package com.woowacourse.moamoa.review.domain.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class WritingReviewBadRequestException extends BadRequestException {

    public WritingReviewBadRequestException() {
        super("스터디에 후기를 작성할 수 없습니다.");
    }
}
