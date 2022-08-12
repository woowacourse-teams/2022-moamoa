package com.woowacourse.moamoa.referenceroom.domain.repository;

import com.woowacourse.moamoa.referenceroom.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLinkRepository extends JpaRepository<Link, Long>, LinkRepository {
}
