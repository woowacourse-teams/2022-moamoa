package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class StudyRoomNotFoundException extends NotFoundException {

    public StudyRoomNotFoundException() {
        super("스터디룸이 존재하지 않습니다.");
    }

    public StudyRoomNotFoundException(final Long studyId) {
        super(String.format("스터디[Id: %d]는 존재하지 않습니다.", studyId));
    }
}
