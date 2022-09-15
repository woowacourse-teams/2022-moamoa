package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeArticle;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeContent;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.NoticeArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummaryResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NoticeArticleService extends AbstractArticleService<NoticeArticle, NoticeContent> {

    private final NoticeArticleDao noticeArticleDao;

    @Autowired
    public NoticeArticleService(final StudyRoomRepository studyRoomRepository,
                                final ArticleRepository<NoticeArticle> articleRepository,
                                final NoticeArticleDao noticeArticleDao) {
        super(studyRoomRepository, articleRepository, NoticeArticle.class);
        this.noticeArticleDao = noticeArticleDao;
    }

    public ArticleResponse getArticle(final Long articleId) {
        final ArticleData data = noticeArticleDao.getById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.NOTICE));
        return new ArticleResponse(data);
    }

    public ArticleSummariesResponse getArticles(final Long studyId, final Pageable pageable) {
        final Page<ArticleData> page = noticeArticleDao.getAllByStudyId(studyId, pageable);

        final List<ArticleSummaryResponse> articles = page.getContent().stream()
                .map(ArticleSummaryResponse::new)
                .collect(Collectors.toList());

        return new ArticleSummariesResponse(articles, page.getNumber(), page.getTotalPages() - 1,
                page.getTotalElements());
    }
}
