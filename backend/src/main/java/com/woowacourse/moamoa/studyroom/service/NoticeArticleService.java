package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeArticle;
import com.woowacourse.moamoa.studyroom.domain.repository.article.NoticeArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.NoticeArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.request.CommunityArticleRequest;
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
    private final NoticeArticleRepository noticeArticleRepository;
    private final NoticeArticleDao noticeArticleDao;

    @Autowired
    public NoticeArticleService(final StudyRoomRepository studyRoomRepository,
                                final NoticeArticleRepository noticeArticleRepository,
                                final NoticeArticleDao noticeArticleDao) {
        this.studyRoomRepository = studyRoomRepository;
        this.noticeArticleRepository = noticeArticleRepository;
        this.noticeArticleDao = noticeArticleDao;
    }

    @Transactional
    public NoticeArticle createArticle(final Long memberId, final Long studyId,
                                 final CommunityArticleRequest request) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final Accessor accessor = new Accessor(memberId, studyId);
        final NoticeArticle article = studyRoom.writeNoticeArticle(accessor, request.getTitle(), request.getContent());
        return noticeArticleRepository.save(article);
    }

    public ArticleResponse getArticle(final Long articleId) {
        final ArticleData data = noticeArticleDao.getById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.NOTICE));
        return new ArticleResponse(data);
    }

    @Transactional
    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId) {
        final NoticeArticle article = noticeArticleRepository
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.NOTICE));

        article.delete(new Accessor(memberId, studyId));
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
    public void updateArticle(final Long memberId, final Long studyId, final Long articleId, final CommunityArticleRequest request) {
        final NoticeArticle article = noticeArticleRepository
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.NOTICE));

        final Accessor accessor = new Accessor(memberId, studyId);

        article.update(accessor, request.getTitle(), request.getContent());
    }
}
