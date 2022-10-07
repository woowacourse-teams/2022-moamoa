package com.woowacourse.moamoa.study.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class StudyNotFoundException extends NotFoundException {

    public StudyNotFoundException() {
        super("스터디가 존재하지 않습니다.");
    }

    public StudyNotFoundException(final Long studyId) {
        super(String.format("스터디[Id: %d]는 존재하지 않습니다.", studyId));
    }
}
