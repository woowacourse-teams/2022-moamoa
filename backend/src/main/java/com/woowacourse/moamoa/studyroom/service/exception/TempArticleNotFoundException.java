package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;

public class TempArticleNotFoundException extends NotFoundException {

    public TempArticleNotFoundException(final Long articleId, final ArticleType type) {
        super(String.format("%d의 식별자를 가진 임시글[%S]이 존재하지 않습니다.", articleId, type));
    }
}
