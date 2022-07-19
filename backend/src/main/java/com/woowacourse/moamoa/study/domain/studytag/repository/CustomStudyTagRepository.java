package com.woowacourse.moamoa.study.domain.studytag.repository;

import com.woowacourse.moamoa.study.domain.studytag.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.studytag.StudySlice;
import org.springframework.data.domain.Pageable;

public interface CustomStudyTagRepository {
    StudySlice searchBy(StudySearchCondition condition, Pageable pageable);
}
