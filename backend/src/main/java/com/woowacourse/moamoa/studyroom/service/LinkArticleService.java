package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import com.woowacourse.moamoa.studyroom.domain.article.LinkContent;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.LinkArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.LinkArticleData;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LinkArticleService extends AbstractArticleService<LinkArticle, LinkContent> {

    private final LinkArticleDao linkArticleDao;

    public LinkArticleService(final StudyRoomRepository studyRoomRepository,
                              final ArticleRepository<LinkArticle> articleRepository,
                              final LinkArticleDao linkArticleDao
    ) {
        super(studyRoomRepository, articleRepository, LinkArticle.class);
        this.linkArticleDao = linkArticleDao;
    }

    public LinksResponse getLinks(final Long studyId, final Pageable pageable) {
        final Slice<LinkArticleData> linkData = linkArticleDao.findAllByStudyId(studyId, pageable);
        return new LinksResponse(linkData.getContent(), linkData.hasNext());
    }
}
