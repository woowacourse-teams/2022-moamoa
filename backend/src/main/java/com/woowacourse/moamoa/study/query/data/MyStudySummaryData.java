package com.woowacourse.moamoa.study.query.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyStudySummaryData {

    private Long id;
    private String title;
    private String studyStatus;
    private Integer currentMemberCount;
    private Integer maxMemberCount;
    private String startDate;
    private String endDate;
}
