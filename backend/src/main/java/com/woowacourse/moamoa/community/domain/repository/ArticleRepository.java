package com.woowacourse.moamoa.community.domain.repository;

import com.woowacourse.moamoa.community.domain.Article;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Optional<Article> findById(Long id);

    void deleteById(Long id);
}
