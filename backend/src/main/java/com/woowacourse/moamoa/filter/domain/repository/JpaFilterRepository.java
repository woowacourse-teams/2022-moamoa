package com.woowacourse.moamoa.filter.domain.repository;

import com.woowacourse.moamoa.filter.domain.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFilterRepository extends JpaRepository<Filter, Long>, FilterRepository {

}
