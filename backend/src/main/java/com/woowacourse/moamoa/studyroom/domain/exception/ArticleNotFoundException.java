package com.woowacourse.moamoa.studyroom.domain.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;

public class ArticleNotFoundException extends NotFoundException {

    public ArticleNotFoundException(final Long articleId, final Class<?> articleType) {
        super(String.format("%d의 식별자를 가진 %s 게시글이 존재하지 않습니다.", articleId, articleType.getSimpleName()));
    }
}
