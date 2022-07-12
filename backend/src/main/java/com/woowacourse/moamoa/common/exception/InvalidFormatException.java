package com.woowacourse.moamoa.common.exception;

public class InvalidFormatException extends IllegalArgumentException {

    public InvalidFormatException() {
        super("잘못된 요청 정보입니다.");
    }
}
