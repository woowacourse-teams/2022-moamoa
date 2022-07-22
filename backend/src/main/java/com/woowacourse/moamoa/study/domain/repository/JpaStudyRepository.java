package com.woowacourse.moamoa.study.domain.repository;

import com.woowacourse.moamoa.study.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStudyRepository extends JpaRepository<Study, Long>, StudyRepository {

}
