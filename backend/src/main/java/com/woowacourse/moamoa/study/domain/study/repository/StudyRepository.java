package com.woowacourse.moamoa.study.domain.study.repository;

import com.woowacourse.moamoa.study.domain.study.Study;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface StudyRepository {

    Slice<Study> findAll(Pageable pageable);

    Slice<Study> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Optional<Study> findById(Long id);
}