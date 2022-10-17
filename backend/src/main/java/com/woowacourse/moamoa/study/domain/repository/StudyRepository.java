package com.woowacourse.moamoa.study.domain.repository;

import com.woowacourse.moamoa.study.domain.Study;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

public interface StudyRepository {

    Study save(Study study);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Study> findById(Long id);

    List<Study> findAll();
}
