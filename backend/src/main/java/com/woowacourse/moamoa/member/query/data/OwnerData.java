package com.woowacourse.moamoa.member.query.data;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OwnerData {

    private Long id;

    private String username;

    private String imageUrl;

    private String profileUrl;

    private LocalDate participationDate;

    private int numberOfStudy;
}
