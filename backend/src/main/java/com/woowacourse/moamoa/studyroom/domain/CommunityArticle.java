package com.woowacourse.moamoa.studyroom.domain;

import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.domain.MemberRole;
import com.woowacourse.moamoa.study.domain.Study;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "community")
public class CommunityArticle extends Article {

    private String title;

    private String content;

    public CommunityArticle(final String title, final String content, final Long authorId, final Study study, final ArticleType type) {
        super(null, authorId, study);
        validateForCreate( authorId, study, type);
        this.title = title;
        this.content = content;
    }

    public CommunityArticle(final Long id, final String title, final String content, final Long authorId,
                         final Study study, final ArticleType type) {
        super(id, authorId, study);
        validateForCreate( authorId, study, type);
        this.title = title;
        this.content = content;
    }

    private void validateForCreate(final Long authorId, final Study study, final ArticleType type) {
        final MemberRole role = study.getRole(authorId);

        if (!type.isCommunity() || role.isNonMember()) {
            throw new NotParticipatedMemberException();
        }
    }

    @Override
    public void update(final Accessor accessor, final String title, final String content) {
        if (!isEditableBy(accessor)) {
            throw new IllegalArgumentException();
        }

        this.title = title;
        this.content = content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommunityArticle that = (CommunityArticle) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle())
                && Objects.equals(getContent(), that.getContent()) && Objects.equals(getAuthorId(),
                that.getAuthorId()) && Objects.equals(getStudy().getId(), that.getStudy().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContent(), getAuthorId(), getStudy().getId());
    }
}

