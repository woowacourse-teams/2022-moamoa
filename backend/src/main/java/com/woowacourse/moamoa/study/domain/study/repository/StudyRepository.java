package com.woowacourse.moamoa.study.domain.study.repository;

import com.woowacourse.moamoa.study.domain.study.Study;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface StudyRepository {

    Slice<Study> findAll(Pageable pageable);

    Slice<Study> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
