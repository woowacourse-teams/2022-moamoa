package com.woowacourse.moamoa.comment.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException() {
        super("댓글을 찾을 수 없습니다.");
    }
}
