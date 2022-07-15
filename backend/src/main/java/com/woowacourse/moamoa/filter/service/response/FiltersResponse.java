package com.woowacourse.moamoa.filter.service.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FiltersResponse {

    private List<FilterResponse> tags;
}
