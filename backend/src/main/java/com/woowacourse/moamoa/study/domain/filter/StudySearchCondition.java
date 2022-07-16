package com.woowacourse.moamoa.study.domain.filter;

import com.woowacourse.moamoa.filter.domain.Filter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudySearchCondition {

    private String title;
    private List<Filter> filters;
}
