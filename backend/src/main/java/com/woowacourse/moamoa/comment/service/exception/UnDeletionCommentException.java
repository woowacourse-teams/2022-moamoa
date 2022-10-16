package com.woowacourse.moamoa.comment.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UnDeletionCommentException extends BadRequestException {

    public UnDeletionCommentException() {
        super("댓글을 삭제할 수 없습니다.");
    }
}
