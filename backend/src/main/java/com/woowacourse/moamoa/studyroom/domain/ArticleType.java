package com.woowacourse.moamoa.studyroom.domain;

public enum ArticleType {

    COMMUNITY, NOTICE;

    public String lowerName() {
        return name().toLowerCase();
    }

    public boolean isCommunity() {
        return this == COMMUNITY;
    }

    public boolean isNotice() {
        return this == NOTICE;
    }
}
