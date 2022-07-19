package com.woowacourse.moamoa.tag.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CategoryId implements Serializable {

    @Column(name = "id")
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
