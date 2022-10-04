package com.woowacourse.moamoa.studyroom.domain.studyroom.repository;

import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import java.util.Optional;

public interface StudyRoomRepository {

    Optional<StudyRoom> findByStudyId(Long studyId);
}
