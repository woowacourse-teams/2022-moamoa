package com.woowacourse.moamoa.community.domain.repository;

import com.woowacourse.moamoa.community.domain.Article;
import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.domain.NoticeArticle;
import com.woowacourse.moamoa.community.service.exception.ArticleNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeArticleRepository implements ArticleRepository {

    private final JpaNoticeArticleRepository noticeArticleRepository;

    public NoticeArticleRepository(
            final JpaNoticeArticleRepository noticeArticleRepository) {
        this.noticeArticleRepository = noticeArticleRepository;
    }

    @Override
    public Article save(Article article) {
        return noticeArticleRepository.save((NoticeArticle) article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        final NoticeArticle article = noticeArticleRepository.findById(id).orElseThrow(
                () -> new ArticleNotFoundException(id));
        return Optional.of(article);
    }

    public void deleteById(final Long articleId) {
        noticeArticleRepository.deleteById(articleId);
    }

    public boolean existsById(final Long id) {
        return noticeArticleRepository.existsById(id);
    }
}
