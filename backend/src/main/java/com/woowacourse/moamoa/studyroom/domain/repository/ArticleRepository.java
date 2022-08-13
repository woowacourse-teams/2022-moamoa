package com.woowacourse.moamoa.studyroom.domain.repository;

import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import java.util.Optional;

public interface ArticleRepository<T extends Article> {

    T save(T article);

    Optional<T> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean isSupportType(ArticleType articleType);
}
