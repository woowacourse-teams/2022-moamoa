package com.woowacourse.moamoa.referenceroom.service.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AuthorResponse {

    private Long id;
    private String username;
    private String imageUrl;
    private String profileUrl;
}
