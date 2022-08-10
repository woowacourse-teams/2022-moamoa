package com.woowacourse.moamoa.community.service.response;

import com.woowacourse.moamoa.member.query.data.MemberData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthorResponse {

    private final long id;
    private final String username;
    private final String imageUrl;
    private final String profileUrl;

    public AuthorResponse(MemberData memberData) {
        this(memberData.getGithubId(), memberData.getUsername(), memberData.getImageUrl(), memberData.getProfileUrl());
    }
}
