package com.woowacourse.moamoa.study.query.data;

import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class StudyDetailsData {

    private final Long id;
    private final String title;
    private final String excerpt;
    private final String thumbnail;
    private final String recruitStatus;
    private final String description;
    private final LocalDate createdAt;
    private final MemberData owner;
    private final Integer currentMemberCount;
    private final Integer maxMemberCount;
    private final LocalDate enrollmentEndDate;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public static StudyDetailsDataBuilder builder() {
        return new StudyDetailsDataBuilder();
    }
}
