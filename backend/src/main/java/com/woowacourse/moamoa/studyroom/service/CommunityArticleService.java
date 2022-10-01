package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.CommunityArticle;
import com.woowacourse.moamoa.studyroom.domain.article.CommunityContent;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.repository.article.CommunityArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.CommunityArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import com.woowacourse.moamoa.studyroom.service.request.CommunityArticleRequest;
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

    private final StudyRoomRepository studyRoomRepository;
    private final CommunityArticleRepository articleRepository;
    private final CommunityArticleDao communityArticleDao;

    public CommunityArticleService(final StudyRoomRepository studyRoomRepository, CommunityArticleRepository articleRepository,
                                   final CommunityArticleDao communityArticleDao) {
        this.studyRoomRepository = studyRoomRepository;
        this.articleRepository = articleRepository;
        this.communityArticleDao = communityArticleDao;
    }

    public ArticleResponse getArticle(final Long articleId) {
        final ArticleData data = communityArticleDao.getById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, CommunityArticle.class));
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

    @Transactional
    public CommunityArticle createArticle(final Long memberId, final Long studyId, final CommunityArticleRequest articleRequest) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final CommunityContent content = articleRequest.createContent();
        final CommunityArticle article = content.createArticle(studyRoom, new Accessor(memberId, studyId));

        return articleRepository.save(article);
    }

    @Transactional
    public void updateArticle(
            final Long memberId, final Long studyId, final Long articleId, final CommunityArticleRequest articleRequest
    ) {
        final CommunityArticle article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, CommunityArticle.class));
        final CommunityContent newContent = articleRequest.createContent();

        article.update(new Accessor(memberId, studyId), newContent);
    }

    @Transactional
    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId) {
        final CommunityArticle article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, CommunityArticle.class));
        final Accessor accessor = new Accessor(memberId, studyId);

        article.delete(accessor);
    }
}
