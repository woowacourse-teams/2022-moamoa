package com.woowacourse.moamoa.studyroom.domain;

public enum ArticleType {

    COMMUNITY, NOTICE;

    public String lowerName() {
        return name().toLowerCase();
    }
}
