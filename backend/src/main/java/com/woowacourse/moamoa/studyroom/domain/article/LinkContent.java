package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkContent implements Content<LinkArticle> {

    @Column(nullable = false)
    private String linkUrl;

    private String description;

    public LinkContent(final String linkUrl, final String description) {
        this.linkUrl = linkUrl;
        this.description = description;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public LinkArticle createArticle(final StudyRoom studyRoom, final Accessor accessor) {
        if (studyRoom.isPermittedAccessor(accessor)) {
            return new LinkArticle(studyRoom, accessor.getMemberId(), this);
        }

        throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.LINK);
    }
}
