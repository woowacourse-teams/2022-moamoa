package com.woowacourse.moamoa.study.query.data;

import java.time.LocalDateTime;
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
    private String recruitmentStatus;
    private LocalDateTime createdDate;
}
