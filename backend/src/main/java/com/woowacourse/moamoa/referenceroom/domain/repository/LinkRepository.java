package com.woowacourse.moamoa.referenceroom.domain.repository;

import com.woowacourse.moamoa.referenceroom.domain.Link;
import java.util.Optional;

public interface LinkRepository {

    Link save(Link link);

    Optional<Link> findById(Long id);
}
