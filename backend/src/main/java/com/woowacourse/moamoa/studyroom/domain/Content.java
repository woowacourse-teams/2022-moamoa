package com.woowacourse.moamoa.studyroom.domain;

public interface Content<T extends Content<T>> {

    void update(T content);
}
