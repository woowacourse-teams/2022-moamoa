package com.woowacourse.moamoa.studyroom.query.data;

import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class LinkData {

    private Long id;
    private MemberData memberData;
    private String linkUrl;
    private String description;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
