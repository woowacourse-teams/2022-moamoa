package com.woowacourse.moamoa.studyroom.domain;

import com.woowacourse.moamoa.studyroom.domain.linkarticle.LinkContent;
import com.woowacourse.moamoa.studyroom.domain.postarticle.PostContent;

public enum ArticleType {

    COMMUNITY(PostContent.class), NOTICE(PostContent.class), LINK(LinkContent.class);

    private final Class<? extends Content> contentType;

    ArticleType(final Class<? extends Content> contentType) {
        this.contentType = contentType;
    }

    public String lowerName() {
        return name().toLowerCase();
    }

    public boolean isValidContentType(Content content) {
        return content.getClass().equals(contentType);
    }

    public Class<? extends Content> getContentType() {
        return contentType;
    }
}
