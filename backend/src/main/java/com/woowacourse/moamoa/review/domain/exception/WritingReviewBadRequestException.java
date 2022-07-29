package com.woowacourse.moamoa.review.domain.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class WritingReviewBadRequestException extends BadRequestException {

    public WritingReviewBadRequestException() {
        super("스터디 시작 전 후기를 작성할 수 없습니다.");
    }
}
