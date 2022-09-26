package com.woowacourse.moamoa.referenceroom.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.moamoa.member.query.data.MemberData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class AuthorResponse {

    @JsonProperty("id")
    private Long githubId;

    private String username;

    private String imageUrl;

    private String profileUrl;

    public AuthorResponse(final MemberData memberData) {
        this(memberData.getGithubId(), memberData.getUsername(), memberData.getImageUrl(), memberData.getProfileUrl());
    }
}
