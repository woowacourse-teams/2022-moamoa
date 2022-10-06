package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class ArticleNotFoundException extends NotFoundException {

    public ArticleNotFoundException(final Long articleId, final String typeName) {
        super(String.format("%d의 식별자를 가진 %s 게시글이 존재하지 않습니다.", articleId, typeName));
    }
}
