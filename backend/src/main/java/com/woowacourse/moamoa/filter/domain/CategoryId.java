package com.woowacourse.moamoa.filter.domain;

public class CategoryId {

    private final Long value;

    public CategoryId(final Long value) {
        this.value = value;
    }

    protected CategoryId() {
        this(null);
    }

    public Long getValue() {
        return value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public static CategoryId empty() {
        return new CategoryId();
    }
}
