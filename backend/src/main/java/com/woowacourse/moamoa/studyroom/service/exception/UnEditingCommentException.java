package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UnEditingCommentException extends BadRequestException {

    public UnEditingCommentException() {
        super("댓글을 삭제할 수 없습니다.");
    }
}
