package com.woowacourse.moamoa.studyroom.domain.repository.article;

import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepositoryFactory {

    private final List<ArticleRepository<? extends Article>> repositories;

    public ArticleRepositoryFactory(List<ArticleRepository<? extends Article>> repositories) {
        this.repositories = repositories;
    }

    @SuppressWarnings("unchecked")
    public ArticleRepository<Article> getRepository(final ArticleType articleType) {
        return (ArticleRepository<Article>) repositories.stream()
                .filter(repository -> repository.isSupportType(articleType))
                .findFirst()
                .orElseThrow();
    }
}
