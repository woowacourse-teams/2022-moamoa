package com.woowacourse.moamoa.study.query.data;

import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyData {

    private final Long id;
    private final String title;
    private final String excerpt;
    private final String thumbnail;
    private final String status;
    private final String description;
    private final MemberData owner;
    private final Integer currentMemberCount;
    private final Integer maxMemberCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime enrollmentEndDate;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
}
