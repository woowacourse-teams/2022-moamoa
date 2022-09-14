package com.woowacourse.moamoa.studyroom.service.response.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.moamoa.member.query.data.MemberData;
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
public class WriterResponse {

    @JsonProperty("id")
    private Long githubId;

    private String username;

    private String imageUrl;

    private String profileUrl;

    public WriterResponse(MemberData memberData) {
        this(memberData.getGithubId(), memberData.getUsername(), memberData.getImageUrl(), memberData.getProfileUrl());
    }
}
