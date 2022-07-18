package com.woowacourse.moamoa.tag.query.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TagResponse {

    private final Long id;
    private final String shortName;
    private final String fullName;
    private final CategoryResponse category;
}
