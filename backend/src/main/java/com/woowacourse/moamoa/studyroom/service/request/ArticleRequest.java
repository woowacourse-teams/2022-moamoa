package com.woowacourse.moamoa.studyroom.service.request;

import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.Content;

public interface ArticleRequest<T extends Content<?>> {

    T createContent();
}
