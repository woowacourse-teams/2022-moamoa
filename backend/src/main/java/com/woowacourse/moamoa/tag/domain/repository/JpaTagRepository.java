package com.woowacourse.moamoa.tag.domain.repository;

import com.woowacourse.moamoa.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTagRepository extends JpaRepository<Tag, Long>, TagRepository {
}
