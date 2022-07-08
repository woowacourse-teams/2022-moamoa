package com.woowacourse.moamoa.study.domain.repository;

import com.woowacourse.moamoa.study.domain.Study;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface StudyRepository {

    Slice<Study> findAll(Pageable pageable);

    Slice<Study> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
