package com.woowacourse.moamoa.studyroom.domain.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;

public class UneditableArticleException extends BadRequestException {

    public UneditableArticleException(final Long studyId, final Accessor accessor, final ArticleType type) {
        super(String.format("스터디[%d]에 접근자[%s]가 %s의 게시글을 수정/추가할 수 없습니다.", studyId, accessor, type));
    }

    public UneditableArticleException(final Long studyId, final Accessor accessor, final Class<? extends Article> articleType) {
        super(String.format("스터디[%d]에 접근자[%s]가 %s의 게시글을 수정/추가할 수 없습니다.", studyId, accessor, articleType.getSimpleName()));
    }
}
