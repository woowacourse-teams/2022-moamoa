package com.woowacourse.moamoa.study.query.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudySummaryData {

    private Long id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String recruitStatus;
}
