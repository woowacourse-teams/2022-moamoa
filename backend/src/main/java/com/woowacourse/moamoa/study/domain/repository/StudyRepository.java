package com.woowacourse.moamoa.study.domain.repository;

import com.woowacourse.moamoa.study.domain.Study;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface StudyRepository {

    Study save(Study study);

    Slice<Study> findAll(Pageable pageable);

    Slice<Study> findByDetailsTitleContainingIgnoreCase(String title, Pageable pageable);

    Optional<Study> findById(Long id);
}
