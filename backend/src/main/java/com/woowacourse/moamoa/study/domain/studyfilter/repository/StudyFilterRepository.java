package com.woowacourse.moamoa.study.domain.studyfilter.repository;

import com.woowacourse.moamoa.study.domain.studyfilter.StudyFilter;
import com.woowacourse.moamoa.study.domain.studyfilter.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.studyfilter.StudySlice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyFilterRepository extends JpaRepository<StudyFilter, Long>, CustomStudyFilterRepository {
    StudySlice searchBy(StudySearchCondition condition, Pageable pageable);
}
