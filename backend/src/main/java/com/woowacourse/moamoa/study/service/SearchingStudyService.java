package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.SearchingTags;
import com.woowacourse.moamoa.study.query.StudyDetailsDao;
import com.woowacourse.moamoa.study.query.StudySummaryDao;
import com.woowacourse.moamoa.study.query.data.StudyDetailsData;
import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.service.response.StudyDetailResponse;
import com.woowacourse.moamoa.tag.query.TagDao;
import com.woowacourse.moamoa.tag.query.response.TagData;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SearchingStudyService {

    private final StudySummaryDao studySummaryDao;
    private final StudyDetailsDao studyDetailsDao;
    private final MemberDao memberDao;
    private final TagDao tagDao;

    public SearchingStudyService(final StudySummaryDao studySummaryDao,
                                 final StudyDetailsDao studyDetailsDao,
                                 final MemberDao memberDao,
                                 final TagDao tagDao
    ) {
        this.studySummaryDao = studySummaryDao;
        this.studyDetailsDao = studyDetailsDao;
        this.memberDao = memberDao;
        this.tagDao = tagDao;
    }

    public StudiesResponse getStudies(final String title, final SearchingTags searchingTags, final Pageable pageable) {
        Slice<StudySummaryData> studyData = studySummaryDao.searchBy(title.trim(), searchingTags, pageable);
        return new StudiesResponse(studyData.getContent(), studyData.hasNext());
    }

    public StudyDetailResponse getStudyDetails(final Long studyId) {
        final StudyDetailsData content = studyDetailsDao.getById(studyId);
        final List<MemberData> participants = memberDao.getByStudyId(studyId);
        final List<TagData> attachedTags = tagDao.getAttachedTagsFrom(studyId);
        return new StudyDetailResponse(content, participants, attachedTags);
    }
}
