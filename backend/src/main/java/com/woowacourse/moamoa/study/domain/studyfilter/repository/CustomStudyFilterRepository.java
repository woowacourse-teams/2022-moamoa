package com.woowacourse.moamoa.study.domain.studyfilter.repository;

import com.woowacourse.moamoa.study.domain.studyfilter.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.studyfilter.StudySlice;
import org.springframework.data.domain.Pageable;

public interface CustomStudyFilterRepository {
    StudySlice searchBy(StudySearchCondition condition, Pageable pageable);
}
