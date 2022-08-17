package com.woowacourse.moamoa.studyroom.domain.repository.studyroom;

import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import java.util.Optional;

public interface StudyRoomRepository {

    Optional<StudyRoom> findByStudyId(Long studyId);
}
