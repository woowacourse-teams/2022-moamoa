package com.woowacourse.moamoa.referenceroom.service.response;

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

    private Long id;

    private String username;

    private String imageUrl;

    private String profileUrl;

    public AuthorResponse(final MemberData memberData) {
        this(memberData.getId(), memberData.getUsername(), memberData.getImageUrl(), memberData.getProfileUrl());
    }
}
