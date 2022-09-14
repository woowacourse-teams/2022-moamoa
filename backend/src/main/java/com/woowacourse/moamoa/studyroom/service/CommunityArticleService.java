package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.CommunityArticle;
import com.woowacourse.moamoa.studyroom.domain.repository.article.CommunityArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.CommunityArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import com.woowacourse.moamoa.studyroom.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.UneditableArticleException;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
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
    private final CommunityArticleRepository communityArticleRepository;
    private final CommunityArticleDao communityArticleDao;

    public CommunityArticleService(
            final StudyRoomRepository studyRoomRepository,
            final CommunityArticleRepository communityArticleRepository,
            final CommunityArticleDao communityArticleDao) {
        this.studyRoomRepository = studyRoomRepository;
        this.communityArticleRepository = communityArticleRepository;
        this.communityArticleDao = communityArticleDao;
    }

    @Transactional
    public CommunityArticle createArticle(final Long memberId, final Long studyId, final ArticleRequest request) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final Accessor accessor = new Accessor(memberId, studyId);
        final CommunityArticle article = studyRoom.writeCommunityArticle(accessor, request.getTitle(), request.getContent());
        return communityArticleRepository.save(article);
    }

    public ArticleResponse getArticle(final Long articleId) {
        final ArticleData data = communityArticleDao.getById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.COMMUNITY));
        return new ArticleResponse(data);
    }

    @Transactional
    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId) {
        final CommunityArticle article = communityArticleRepository
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.COMMUNITY));

        if (!article.isEditableBy(new Accessor(memberId, studyId))) {
            throw new UneditableArticleException(studyId, new Accessor(memberId, studyId), ArticleType.COMMUNITY);
        }

        communityArticleRepository.deleteById(articleId);
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
    public void updateArticle(final Long memberId, final Long studyId, final Long articleId,
                              final ArticleRequest request) {
        final CommunityArticle article = communityArticleRepository
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.COMMUNITY));

        final Accessor accessor = new Accessor(memberId, studyId);

        if (!article.isEditableBy(accessor)) {
            throw new UneditableArticleException(studyId, accessor, ArticleType.COMMUNITY);
        }

        article.update(accessor, request.getTitle(), request.getContent());
    }
}
