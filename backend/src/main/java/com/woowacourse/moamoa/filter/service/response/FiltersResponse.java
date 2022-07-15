package com.woowacourse.moamoa.filter.service.response;

import com.woowacourse.moamoa.filter.infra.response.FilterResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FiltersResponse {

    private final List<FilterResponse> filters;
}
