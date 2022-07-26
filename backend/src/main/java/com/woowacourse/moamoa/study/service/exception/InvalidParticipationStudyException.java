package com.woowacourse.moamoa.study.service.exception;

public class InvalidParticipationStudyException extends RuntimeException {

    public InvalidParticipationStudyException() {
        super("스터디 가입이 불가능합니다.");
    }
}
