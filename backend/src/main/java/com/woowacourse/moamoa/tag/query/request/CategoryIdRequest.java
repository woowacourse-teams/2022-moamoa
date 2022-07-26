package com.woowacourse.moamoa.tag.query.request;

public class CategoryIdRequest {

    private final Long value;

    public CategoryIdRequest(final long value) {
        this.value = value;
    }

    private CategoryIdRequest() {
        value = null;
    }

    public Long getValue() {
        return value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public static CategoryIdRequest empty() {
        return new CategoryIdRequest();
    }
}
