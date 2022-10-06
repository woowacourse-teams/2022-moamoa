package com.woowacourse.moamoa.studyroom.domain.review.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class ReviewNotWrittenInTheStudyException extends BadRequestException {

    public ReviewNotWrittenInTheStudyException() {
        super("해당 스터디에 작성된 후기가 아닙니다.");
    }
}
