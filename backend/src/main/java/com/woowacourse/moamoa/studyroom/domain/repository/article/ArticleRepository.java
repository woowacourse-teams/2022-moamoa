package com.woowacourse.moamoa.studyroom.domain.repository.article;

import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import java.util.Optional;

public interface ArticleRepository<A extends Article<? extends Content<A>>> {

    A save(A article);

    Optional<A> findById(Long id);

    boolean existsById(Long id);
}
