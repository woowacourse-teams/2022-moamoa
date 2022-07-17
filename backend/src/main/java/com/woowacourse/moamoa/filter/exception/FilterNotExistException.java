package com.woowacourse.moamoa.filter.exception;

public class FilterNotExistException extends RuntimeException {

    public FilterNotExistException() {
        super("필터가 존재하지 않습니다.");
    }
}
