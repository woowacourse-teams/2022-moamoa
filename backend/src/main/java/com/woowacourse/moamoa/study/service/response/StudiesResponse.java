package com.woowacourse.moamoa.study.service.response;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudiesResponse {

    private List<StudyResponse> studies;
    private boolean hasNext;
    private Long id;
    private String createdDate;

    private StudiesResponse(final List<StudyResponse> studies, final boolean hasNext, final Long id,
                           final String createdDate) {
        this.studies = studies;
        this.hasNext = hasNext;
        this.id = id;
        this.createdDate = createdDate;
    }

    public static StudiesResponse of(final List<StudySummaryData> studySummaryData,
                                     final Map<Long, List<TagSummaryData>> studyTags, final boolean hasNext) {
        final List<StudyResponse> studies = getStudyResponses(studySummaryData, studyTags);
        if (studies.isEmpty()) {
            return new StudiesResponse(studies, hasNext, null, "");
        }

        final StudySummaryData lastStudy = getLastStudyResponse(studySummaryData);
        return new StudiesResponse(studies, hasNext, lastStudy.getId(), lastStudy.getCreatedDate().format(ISO_DATE_TIME));
    }

    private static List<StudyResponse> getStudyResponses(
            final List<StudySummaryData> studiesSummaryData,
            final Map<Long, List<TagSummaryData>> studyTags
    ) {
        return studiesSummaryData.stream()
                .map(studySummaryData -> new StudyResponse(studySummaryData, studyTags.get(studySummaryData.getId())))
                .collect(Collectors.toList());
    }

    private static StudySummaryData getLastStudyResponse(final List<StudySummaryData> studies) {
        return studies.get(studies.size() - 1);
    }
}
