package com.woowacourse.moamoa.community.domain.repository;

import com.woowacourse.moamoa.community.domain.Article;
import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.service.exception.ArticleNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityArticleRepository implements ArticleRepository {

    private final JpaCommunityArticleRepository jpaCommunityArticleRepository;

    public CommunityArticleRepository(
            final JpaCommunityArticleRepository jpaCommunityArticleRepository) {
        this.jpaCommunityArticleRepository = jpaCommunityArticleRepository;
    }

    @Override
    public Article save(Article article) {
        return jpaCommunityArticleRepository.save((CommunityArticle) article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        final CommunityArticle article = jpaCommunityArticleRepository.findById(id).orElseThrow(
                () -> new ArticleNotFoundException(id));
        return Optional.of(article);
    }

    public void deleteById(final Long articleId) {
        jpaCommunityArticleRepository.deleteById(articleId);
    }

    public boolean existsById(final Long id) {
        return jpaCommunityArticleRepository.existsById(id);
    }
}
