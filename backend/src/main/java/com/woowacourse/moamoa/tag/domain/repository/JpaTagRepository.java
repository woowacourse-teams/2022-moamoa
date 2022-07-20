package com.woowacourse.moamoa.tag.domain.repository;

import com.woowacourse.moamoa.tag.domain.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTagRepository extends JpaRepository<Tag, Long>, TagRepository {

    Optional<Tag> findByName(String name);
}
