package com.woowacourse.moamoa.studyroom.domain.linkarticle;

import com.woowacourse.moamoa.studyroom.domain.Content;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkContent implements Content<LinkContent> {

    private String linkUrl;

    private String description;

    public LinkContent(final String linkUrl, final String description) {
        this.linkUrl = linkUrl;
        this.description = description;
    }

    @Override
    public void update(final LinkContent content) {
        this.linkUrl = content.linkUrl;
        this.description = content.description;
    }
}
