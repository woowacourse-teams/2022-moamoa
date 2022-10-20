package com.woowacourse.moamoa.comment.service.response;

import static lombok.AccessLevel.PRIVATE;

import com.woowacourse.moamoa.member.query.data.MemberData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor(access = PRIVATE)
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PRIVATE)
@ToString
public class AuthorResponse {

    private Long id;
    private String username;
    private String imageUrl;
    private String profileUrl;

    public AuthorResponse(MemberData memberData) {
        this(memberData.getId(), memberData.getUsername(), memberData.getImageUrl(), memberData.getProfileUrl());
    }
}
