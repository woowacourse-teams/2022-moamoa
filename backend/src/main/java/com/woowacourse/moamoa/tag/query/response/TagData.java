package com.woowacourse.moamoa.tag.query.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TagData {

    private final Long id;
    private final String name;
    private final String description;
    private final CategoryData category;
}
