package com.woowacourse.moamoa.studyroom.domain.article;

public enum ArticleType {

    COMMUNITY, NOTICE;

    public String lowerName() {
        return name().toLowerCase();
    }
}
