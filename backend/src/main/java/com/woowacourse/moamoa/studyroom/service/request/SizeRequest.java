package com.woowacourse.moamoa.studyroom.service.request;

public class SizeRequest {

    private final Integer value;

    private SizeRequest() {
        value = null;
    }

    public SizeRequest(final int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public boolean isMoreThan(int value) {
        if (isEmpty()) {
            throw new IllegalStateException("SizeRequest에 value가 null 입니다.");
        }
        return getValue() > value;
    }

    public static SizeRequest empty() {
        return new SizeRequest();
    }
}
