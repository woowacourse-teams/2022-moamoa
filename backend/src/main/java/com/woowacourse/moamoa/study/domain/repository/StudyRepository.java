package com.woowacourse.moamoa.study.domain.repository;

import com.woowacourse.moamoa.study.domain.Study;
import java.util.Optional;

public interface StudyRepository {

    Study save(Study study);

    Optional<Study> findById(Long id);
}
