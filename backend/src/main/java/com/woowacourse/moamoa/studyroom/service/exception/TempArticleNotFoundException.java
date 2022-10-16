package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class TempArticleNotFoundException extends NotFoundException {

    public TempArticleNotFoundException(final Long articleId) {
        super(String.format("%d의 식별자를 가진 임시 게시글이 존재하지 않습니다.", articleId));
    }
}
