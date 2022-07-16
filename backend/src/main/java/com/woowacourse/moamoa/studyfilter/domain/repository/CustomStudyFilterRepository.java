package com.woowacourse.moamoa.studyfilter.domain.repository;

import com.woowacourse.moamoa.studyfilter.domain.StudySearchCondition;
import com.woowacourse.moamoa.studyfilter.domain.StudySlice;
import org.springframework.data.domain.Pageable;

public interface CustomStudyFilterRepository {
    StudySlice searchBy(StudySearchCondition condition, Pageable pageable);
}
