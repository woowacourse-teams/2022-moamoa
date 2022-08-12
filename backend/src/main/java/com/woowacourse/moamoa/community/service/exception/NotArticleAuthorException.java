package com.woowacourse.moamoa.community.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class NotArticleAuthorException extends BadRequestException {

    public NotArticleAuthorException(final Long articleId, final Long authorId) {
        super("게시글[" + articleId + "]은 작성자[" + authorId + "]가 아닙니다.");
    }
}
