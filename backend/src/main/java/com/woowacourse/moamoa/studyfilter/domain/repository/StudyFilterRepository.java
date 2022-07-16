package com.woowacourse.moamoa.studyfilter.domain.repository;

import com.woowacourse.moamoa.studyfilter.domain.StudyFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyFilterRepository extends JpaRepository<StudyFilter, Long>, CustomStudyFilterRepository {
}
