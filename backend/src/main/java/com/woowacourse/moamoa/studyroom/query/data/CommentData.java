package com.woowacourse.moamoa.studyroom.query.data;

import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class CommentData {

    private Long id;
    private MemberData member;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
    private String content;
}
