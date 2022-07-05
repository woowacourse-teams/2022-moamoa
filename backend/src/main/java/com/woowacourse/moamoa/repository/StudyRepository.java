package com.woowacourse.moamoa.repository;

import com.woowacourse.moamoa.domain.Study;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Slice<Study> findAllBy(Pageable pageable);
}
