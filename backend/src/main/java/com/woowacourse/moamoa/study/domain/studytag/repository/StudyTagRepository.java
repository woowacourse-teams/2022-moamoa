package com.woowacourse.moamoa.study.domain.studytag.repository;

import com.woowacourse.moamoa.study.domain.studytag.StudyTag;
import com.woowacourse.moamoa.study.domain.studytag.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.studytag.StudySlice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyTagRepository extends JpaRepository<StudyTag, Long>, CustomStudyTagRepository {
    StudySlice searchBy(StudySearchCondition condition, Pageable pageable);
}
