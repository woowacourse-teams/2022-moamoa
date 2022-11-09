package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UnWrittenCommentException extends BadRequestException {

    public UnWrittenCommentException() {
        super("댓글을 작성하거나 수정할 수 없습니다.");
    }
}
