package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import com.woowacourse.moamoa.studyroom.domain.article.LinkContent;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.repository.article.LinkArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.LinkArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.LinkArticleData;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LinkArticleService {

    private final StudyRoomRepository studyRoomRepository;
    private final LinkArticleRepository linkArticleRepository;
    private final LinkArticleDao linkArticleDao;

    public LinkArticleService(final StudyRoomRepository studyRoomRepository,
                              final LinkArticleRepository linkArticleRepository,
                              final LinkArticleDao linkArticleDao
    ) {
        this.studyRoomRepository = studyRoomRepository;
        this.linkArticleRepository = linkArticleRepository;
        this.linkArticleDao = linkArticleDao;
    }

    public LinksResponse getLinks(final Long studyId, final Pageable pageable) {
        final Slice<LinkArticleData> linkData = linkArticleDao.findAllByStudyId(studyId, pageable);
        return new LinksResponse(linkData.getContent(), linkData.hasNext());
    }

    @Transactional
    public LinkArticle createArticle(final Long memberId, final Long studyId, final LinkContent content) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(() -> new StudyNotFoundException(studyId));
        final LinkArticle article = LinkArticle.create(studyRoom, new Accessor(memberId, studyId), content);

        return linkArticleRepository.save(article);
    }

    @Transactional
    public void updateArticle(
            final Long memberId, final Long studyId, final Long articleId, final LinkContent newContent
    ) {
        final LinkArticle article = linkArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, LinkArticle.class));

        article.update(new Accessor(memberId, studyId), newContent);
    }

    @Transactional
    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId) {
        final LinkArticle article = linkArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, LinkArticle.class));
        final Accessor accessor = new Accessor(memberId, studyId);

        article.delete(accessor);
    }
}
