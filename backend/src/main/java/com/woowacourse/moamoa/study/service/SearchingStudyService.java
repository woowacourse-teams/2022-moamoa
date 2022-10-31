package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.query.data.ParticipatingMemberData;
import com.woowacourse.moamoa.study.query.SearchingTags;
import com.woowacourse.moamoa.study.query.StudyDetailsDao;
import com.woowacourse.moamoa.study.query.StudySummaryDao;
import com.woowacourse.moamoa.study.query.data.StudyDetailsData;
import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.service.response.StudyDetailResponse;
import com.woowacourse.moamoa.tag.query.TagDao;
import com.woowacourse.moamoa.tag.query.response.TagData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    public SearchingStudyService(
            final StudySummaryDao studySummaryDao,
            final StudyDetailsDao studyDetailsDao,
            final MemberDao memberDao,
            final TagDao tagDao
    ) {
        this.studySummaryDao = studySummaryDao;
        this.studyDetailsDao = studyDetailsDao;
        this.memberDao = memberDao;
        this.tagDao = tagDao;
    }

    public StudiesResponse getStudies(final String title, final SearchingTags searchingTags, final Long id,
                                      final LocalDateTime createdAt, final int size) {
        final Slice<StudySummaryData> studyData = studySummaryDao.searchBy(title.trim(), searchingTags, id, createdAt,
                size);

        final List<Long> studyIds = studyData.getContent().stream()
                .map(StudySummaryData::getId)
                .collect(Collectors.toList());
        final Map<Long, List<TagSummaryData>> studyTags = tagDao.findTagsByStudyIds(studyIds);

        return new StudiesResponse(studyData.getContent(), studyTags, studyData.hasNext());
    }

    public StudyDetailResponse getStudyDetails(final Long studyId) {
        final StudyDetailsData content = studyDetailsDao.findBy(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final List<ParticipatingMemberData> participants = memberDao.findMembersByStudyId(studyId);

        final List<TagData> attachedTags = tagDao.findTagsByStudyId(studyId);
        return new StudyDetailResponse(content, participants, attachedTags);
    }
}
