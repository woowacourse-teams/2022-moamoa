package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudyResponse {

    private Long id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String recruitmentStatus;
    private List<TagSummaryData> tags;

    public StudyResponse(
            final StudySummaryData studySummaryData,
            final List<TagSummaryData> studyTags
    ) {
        this(studySummaryData.getId(), studySummaryData.getTitle(), studySummaryData.getExcerpt(),
                studySummaryData.getThumbnail(), studySummaryData.getRecruitmentStatus(), studyTags);
    }
}
