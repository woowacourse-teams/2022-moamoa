package com.woowacourse.moamoa.tag.exception;

public class InvalidCategoryNameException extends RuntimeException {
    public InvalidCategoryNameException() {
        super("적절하지 못한 카테고리 이름입니다.");
    }
}
