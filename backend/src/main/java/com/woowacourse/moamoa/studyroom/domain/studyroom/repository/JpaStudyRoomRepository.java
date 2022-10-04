package com.woowacourse.moamoa.studyroom.domain.studyroom.repository;

import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaStudyRoomRepository extends JpaRepository<StudyRoom, Long>, StudyRoomRepository {
}
