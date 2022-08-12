package com.woowacourse.moamoa.community.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class ArticleNotFoundException extends NotFoundException {

    public ArticleNotFoundException(long articleId) {
        super(articleId + "의 식별자를 가진 게시글이 존재하지 않습니다.");
    }
}
