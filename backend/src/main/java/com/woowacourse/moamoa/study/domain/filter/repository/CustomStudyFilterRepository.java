package com.woowacourse.moamoa.study.domain.filter.repository;

import com.woowacourse.moamoa.study.domain.filter.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.filter.StudySlice;
import org.springframework.data.domain.Pageable;

public interface CustomStudyFilterRepository {
    StudySlice searchBy(StudySearchCondition condition, Pageable pageable);
}
