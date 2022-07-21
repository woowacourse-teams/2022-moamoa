package com.woowacourse.moamoa.study.exception;

public class StudyNotExistException extends RuntimeException {

    public StudyNotExistException() {
        super("스터디가 존재하지 않습니다.");
    }
}
