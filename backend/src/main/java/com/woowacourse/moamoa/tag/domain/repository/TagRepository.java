package com.woowacourse.moamoa.tag.domain.repository;

import com.woowacourse.moamoa.tag.domain.Tag;
import java.util.List;
import java.util.Optional;

public interface TagRepository {

    List<Tag> findAllByNameContainingIgnoreCase(String name);

    Optional<Tag> findById(Long id);
}
