package com.woowacourse.moamoa.studyroom.domain.repository.studyroom;

import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaStudyRoomRepository extends JpaRepository<StudyRoom, Long>,
        StudyRoomRepository {
}
