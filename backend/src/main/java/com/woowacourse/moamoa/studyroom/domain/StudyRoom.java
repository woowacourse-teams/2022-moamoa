package com.woowacourse.moamoa.studyroom.domain;

import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.studyroom.domain.linkarticle.LinkArticle;
import com.woowacourse.moamoa.studyroom.domain.linkarticle.LinkContent;
import com.woowacourse.moamoa.studyroom.domain.postarticle.CommunityArticle;
import com.woowacourse.moamoa.studyroom.domain.postarticle.NoticeArticle;
import com.woowacourse.moamoa.studyroom.domain.postarticle.PostContent;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "study")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRoom {

    @Id
    @Column(name = "id", updatable = false, insertable = false)
    private Long studyId;

    @Embedded
    private PermittedAccessors permittedAccessors;

    public StudyRoom(Long studyId, Long ownerId, Set<Long> participants) {
        this.studyId = studyId;
        this.permittedAccessors = new PermittedAccessors(ownerId, participants);
    }

    boolean isOwner(final Accessor accessor) {
        return studyId.equals(accessor.getStudyId()) && permittedAccessors.isOwner(accessor);
    }

    public boolean isPermittedAccessor(final Accessor accessor) {
        return studyId.equals(accessor.getStudyId()) && permittedAccessors.isPermittedAccessor(accessor);
    }

    public Article writeArticle(final Accessor accessor, final Content content, final ArticleType type) {
        if (!type.isValidContentType(content)) {
            throw new IllegalArgumentException();
        }

        if (type == ArticleType.COMMUNITY && isPermittedAccessor(accessor)) {
            return new CommunityArticle((PostContent) content, accessor.getMemberId(), this);
        }

        if (type == ArticleType.LINK && isPermittedAccessor(accessor)) {
            return new LinkArticle((LinkContent) content, accessor.getMemberId(), this);
        }

        if (type == ArticleType.NOTICE && isOwner(accessor)) {
            return new NoticeArticle((PostContent) content, accessor.getMemberId(), this);
        }

        throw new NotParticipatedMemberException();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudyRoom that = (StudyRoom) o;
        return Objects.equals(studyId, that.studyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studyId);
    }
}
