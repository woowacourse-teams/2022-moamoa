package com.woowacourse.moamoa.study.domain.filter.repository;

import com.woowacourse.moamoa.study.domain.filter.StudyFilter;
import com.woowacourse.moamoa.study.domain.filter.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.filter.StudySlice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyFilterRepository extends JpaRepository<StudyFilter, Long>, CustomStudyFilterRepository {
    StudySlice searchBy(StudySearchCondition condition, Pageable pageable);
}
