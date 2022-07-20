package com.woowacourse.moamoa.tag.exception;

public class TagNotExistException extends RuntimeException {

    public TagNotExistException() {
        super("필터가 존재하지 않습니다.");
    }
}
