package com.woowacourse.moamoa.comment.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UnwrittenCommentException extends BadRequestException {

    public UnwrittenCommentException() {
        super("내가 작성한 댓글이 아닙니다.");
    }
}
