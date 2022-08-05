package com.woowacourse.moamoa.study.service.exception;

import com.woowacourse.moamoa.common.exception.NotFoundException;

public class StudyNotFoundException extends NotFoundException {

    public StudyNotFoundException() {
        super("스터디가 존재하지 않습니다.");
    }
}
