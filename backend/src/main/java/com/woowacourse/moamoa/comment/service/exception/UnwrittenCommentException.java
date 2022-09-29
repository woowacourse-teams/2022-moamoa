package com.woowacourse.moamoa.comment.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UnwrittenCommentException extends BadRequestException {

    public UnwrittenCommentException() {
        super("댓글을 작성할 수 없습니다.");
    }
}
