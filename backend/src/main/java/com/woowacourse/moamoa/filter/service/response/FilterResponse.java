package com.woowacourse.moamoa.filter.service.response;

import com.woowacourse.moamoa.filter.domain.Filter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilterResponse {

    private Long id;
    private String tagName;

    public FilterResponse(Filter filter) {
        this(filter.getId(), filter.getName());
    }
}
