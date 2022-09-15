package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.studyroom.query.LinkArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.LinkArticleData;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LinkArticleService {

    private final LinkArticleDao linkArticleDao;

    public LinkArticleService(final LinkArticleDao linkArticleDao) {
        this.linkArticleDao = linkArticleDao;
    }

    public LinksResponse getLinks(final Long studyId, final Pageable pageable) {
        final Slice<LinkArticleData> linkData = linkArticleDao.findAllByStudyId(studyId, pageable);
        return new LinksResponse(linkData.getContent(), linkData.hasNext());
    }
}
