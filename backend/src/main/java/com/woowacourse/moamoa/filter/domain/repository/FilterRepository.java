package com.woowacourse.moamoa.filter.domain.repository;

import com.woowacourse.moamoa.filter.domain.Filter;
import java.util.List;

public interface FilterRepository {

    List<Filter> findAllByNameContainingIgnoreCase(String name);
}
