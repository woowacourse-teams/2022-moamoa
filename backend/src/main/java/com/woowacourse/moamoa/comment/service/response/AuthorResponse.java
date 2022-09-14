package com.woowacourse.moamoa.comment.service.response;

import com.woowacourse.moamoa.member.query.data.MemberData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class AuthorResponse {

    private Long githubId;
    private String username;
    private String imageUrl;
    private String profileUrl;

    public AuthorResponse(MemberData memberData) {
        this(memberData.getGithubId(), memberData.getUsername(), memberData.getImageUrl(), memberData.getProfileUrl());
    }
}
