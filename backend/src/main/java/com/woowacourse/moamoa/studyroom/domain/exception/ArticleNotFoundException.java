package com.woowacourse.moamoa.studyroom.domain.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;

public class ArticleNotFoundException extends NotFoundException {

    public ArticleNotFoundException(long articleId, ArticleType type) {
        super(String.format("%d의 식별자를 가진 타입 %s의 게시글이 존재하지 않습니다.", articleId, type));
    }
}
