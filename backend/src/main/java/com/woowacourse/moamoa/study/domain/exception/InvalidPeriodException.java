package com.woowacourse.moamoa.study.domain.exception;

public class InvalidPeriodException extends RuntimeException {

    public InvalidPeriodException() {
        super("잘못된 기간 설정입니다.");
    }
}
