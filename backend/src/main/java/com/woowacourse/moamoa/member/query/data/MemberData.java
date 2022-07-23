package com.woowacourse.moamoa.member.query.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
public class MemberData {

    @JsonProperty("id")
    private Long githubId;

    private String username;

    private String imageUrl;

    private String profileUrl;
}
