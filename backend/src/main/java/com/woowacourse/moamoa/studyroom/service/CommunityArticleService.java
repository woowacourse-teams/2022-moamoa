package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.query.CommunityArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummaryResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommunityArticleService {

    private final CommunityArticleDao communityArticleDao;

    public CommunityArticleService(final CommunityArticleDao communityArticleDao) {
        this.communityArticleDao = communityArticleDao;
    }

    public ArticleResponse getArticle(final Long articleId) {
        final ArticleData data = communityArticleDao.getById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.COMMUNITY));
        return new ArticleResponse(data);
    }

    public ArticleSummariesResponse getArticles(final Long studyId, final Pageable pageable) {
        final Page<ArticleData> page = communityArticleDao.getAllByStudyId(studyId, pageable);

        final List<ArticleSummaryResponse> articles = page.getContent().stream()
                .map(ArticleSummaryResponse::new)
                .collect(Collectors.toList());

        return new ArticleSummariesResponse(articles, page.getNumber(), page.getTotalPages() - 1,
                page.getTotalElements());
    }
}
