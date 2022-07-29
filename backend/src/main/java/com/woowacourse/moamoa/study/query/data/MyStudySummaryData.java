package com.woowacourse.moamoa.study.query.data;

import com.woowacourse.moamoa.study.domain.StudyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyStudySummaryData {

    private Long id;
    private String title;
    private StudyStatus studyStatus;
    private Integer currentMemberCount;
    private Integer maxMemberCount;
    private String startDate;
    private String endDate;
}
