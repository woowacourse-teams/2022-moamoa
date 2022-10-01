package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeArticle;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeContent;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.repository.article.NoticeArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.NoticeArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummaryResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NoticeArticleService {

    private final StudyRoomRepository studyRoomRepository;
    private final NoticeArticleRepository articleRepository;
    private final NoticeArticleDao noticeArticleDao;

    @Autowired
    public NoticeArticleService(final StudyRoomRepository studyRoomRepository,
                                final NoticeArticleRepository articleRepository,
                                final NoticeArticleDao noticeArticleDao) {
        this.studyRoomRepository = studyRoomRepository;
        this.articleRepository = articleRepository;
        this.noticeArticleDao = noticeArticleDao;
    }

    public ArticleResponse getArticle(final Long articleId) {
        final ArticleData data = noticeArticleDao.getById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, NoticeArticle.class));
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

    @Transactional
    public NoticeArticle createArticle(final Long memberId, final Long studyId, final NoticeContent content) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(() -> new StudyNotFoundException(studyId));
        final NoticeArticle article = NoticeArticle.create(studyRoom, new Accessor(memberId, studyId), content);

        return articleRepository.save(article);
    }

    @Transactional
    public void updateArticle(
            final Long memberId, final Long studyId, final Long articleId, final NoticeContent newContent
    ) {
        final NoticeArticle article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, NoticeArticle.class));

        article.update(new Accessor(memberId, studyId), newContent);
    }

    @Transactional
    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId) {
        final NoticeArticle article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, NoticeArticle.class));
        final Accessor accessor = new Accessor(memberId, studyId);

        article.delete(accessor);
    }

}
