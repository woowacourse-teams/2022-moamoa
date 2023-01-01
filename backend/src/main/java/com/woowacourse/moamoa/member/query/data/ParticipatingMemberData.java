package com.woowacourse.moamoa.member.query.data;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ParticipatingMemberData {

    private Long id;

    private String username;

    private String imageUrl;

    private String profileUrl;

    private LocalDate participationDate;

    private int numberOfStudy;
}
