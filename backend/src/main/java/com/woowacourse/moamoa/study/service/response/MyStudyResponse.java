package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.domain.StudyStatus;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyStudyResponse {

    private Long id;
    private String title;
    private StudyStatus studyStatus;
    private Integer currentMemberCount;
    private Integer maxMemberCount;
    private String startDate;
    private String endDate;
    private MemberData owner;
    private List<TagSummaryData> tags;

    public MyStudyResponse(MyStudySummaryData myStudy, MemberData owner, List<TagSummaryData> tags) {
        this.id = myStudy.getId();
        this.title = myStudy.getTitle();
        this.studyStatus = myStudy.getStudyStatus();
        this.currentMemberCount = myStudy.getCurrentMemberCount();
        this.maxMemberCount = myStudy.getMaxMemberCount();
        this.startDate = myStudy.getStartDate();
        this.endDate = myStudy.getEndDate();
        this.owner = owner;
        this.tags = tags;
    }
}
