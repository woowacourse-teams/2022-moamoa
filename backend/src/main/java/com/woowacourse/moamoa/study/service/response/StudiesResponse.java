package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudiesResponse {

    private List<StudyResponse> studies;
    private boolean hasNext;

    public StudiesResponse(
            final List<StudySummaryData> studySummaryData,
            final Map<Long, List<TagSummaryData>> studyTags,
            final boolean hasNext
    ) {

        this.studies = getStudyResponses(studySummaryData, studyTags);
        this.hasNext = hasNext;
    }

    private List<StudyResponse> getStudyResponses(
            final List<StudySummaryData> studiesSummaryData,
            final Map<Long, List<TagSummaryData>> studyTags
    ) {
        return studiesSummaryData.stream()
                .map(studySummaryData -> new StudyResponse(studySummaryData, studyTags.get(studySummaryData.getId())))
                .collect(Collectors.toList());
    }
}
