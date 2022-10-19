package com.woowacourse.acceptance.document;

import io.restassured.specification.RequestSpecification;

public abstract class Document {

    private final RequestSpecification spec;

    protected Document(final RequestSpecification spec) {
        this.spec = spec;
    }

    public RequestSpecification spec() {
        return spec;
    }
}
