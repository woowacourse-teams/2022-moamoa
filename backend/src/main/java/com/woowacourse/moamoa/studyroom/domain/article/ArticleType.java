package com.woowacourse.moamoa.studyroom.domain.article;

public enum ArticleType {

    COMMUNITY, NOTICE, LINK;

    public String lowerName() {
        return name().toLowerCase();
    }
}
