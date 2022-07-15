package com.woowacourse.moamoa.filter.infra.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryResponse {

    private final Long id;
    private final String name;
}
