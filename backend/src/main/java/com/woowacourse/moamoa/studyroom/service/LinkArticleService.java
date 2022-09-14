package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import com.woowacourse.moamoa.studyroom.domain.repository.article.LinkArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.LinkArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.LinkArticleData;
import com.woowacourse.moamoa.studyroom.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LinkArticleService {

    private final StudyRoomRepository studyRoomRepository;
    private final LinkArticleRepository linkArticleRepository;
    private final LinkArticleDao linkArticleDao;

    public LinkArticleService(
            final StudyRoomRepository studyRoomRepository, final LinkArticleRepository linkArticleRepository,
            final LinkArticleDao linkArticleDao
    ) {
        this.studyRoomRepository = studyRoomRepository;
        this.linkArticleRepository = linkArticleRepository;
        this.linkArticleDao = linkArticleDao;
    }

    public LinkArticle createLink(
            final Long memberId, final Long studyId, final LinkArticleRequest linkArticleRequest
    ) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final LinkArticle linkArticle = studyRoom.writeLinkArticle(
                new Accessor(memberId, studyId), linkArticleRequest.getLinkUrl(), linkArticleRequest.getDescription());

        return linkArticleRepository.save(linkArticle);
    }

    public void updateLink(
            final Long memberId, final Long studyId, final Long articleId, final LinkArticleRequest linkArticleRequest
    ) {
        final LinkArticle linkArticle = linkArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.LINK));

        final Accessor accessor = new Accessor(memberId, studyId);
        final String linkUrl = linkArticleRequest.getLinkUrl();
        final String description = linkArticleRequest.getDescription();

        linkArticle.update(accessor, linkUrl, description);
    }

    public void deleteLink(final Long memberId, final Long studyId, final Long articleId) {
        final LinkArticle linkArticle = linkArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.LINK));

        final Accessor accessor = new Accessor(memberId, studyId);

        linkArticle.delete(accessor);
    }

    public LinksResponse getLinks(final Long studyId, final Pageable pageable) {
        final Slice<LinkArticleData> linkData = linkArticleDao.findAllByStudyId(studyId, pageable);
        return new LinksResponse(linkData.getContent(), linkData.hasNext());
    }
}
