package com.woowacourse.moamoa.filter.domain.repository;

import com.woowacourse.moamoa.filter.domain.Filter;
import java.util.List;
import java.util.Optional;

public interface FilterRepository {

    List<Filter> findAllByNameContainingIgnoreCase(String name);
    Optional<Filter> findByName(String name);
}
