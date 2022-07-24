package com.woowacourse.moamoa.study.service.exception;

public class StudyNotFoundException extends RuntimeException {

    public StudyNotFoundException() {
        super("스터디가 존재하지 않습니다.");
    }
}
