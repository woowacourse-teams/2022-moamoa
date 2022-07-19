package com.woowacourse.moamoa.study.domain.study.repository;

import com.woowacourse.moamoa.study.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStudyRepository extends JpaRepository<Study, Long>, StudyRepository {

}
