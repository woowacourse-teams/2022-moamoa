package com.woowacourse.moamoa.studyroom.service.request;

import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.Content;

public interface AbstractArticleRequest<T extends Content<? extends Article<T>>> {

    T mapToContent();
}
