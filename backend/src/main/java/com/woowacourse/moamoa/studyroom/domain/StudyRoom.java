package com.woowacourse.moamoa.studyroom.domain;

import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.studyroom.domain.review.AssociatedStudy;
import com.woowacourse.moamoa.studyroom.domain.review.Review;
import com.woowacourse.moamoa.studyroom.domain.review.Reviewer;
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
    private PermittedParticipants permittedParticipants;

    public StudyRoom(Long studyId, Long ownerId, Set<Long> participants) {
        this.studyId = studyId;
        this.permittedParticipants = new PermittedParticipants(ownerId, participants);
    }

    boolean isOwner(final Accessor accessor) {
        return studyId.equals(accessor.getStudyId()) && permittedParticipants.isOwner(accessor);
    }

    public boolean isPermittedAccessor(final Accessor accessor) {
        return studyId.equals(accessor.getStudyId()) && permittedParticipants.isPermittedAccessor(accessor);
    }

    public Article write(final Accessor accessor, final String title, final String content, final ArticleType type) {
        if (type == ArticleType.COMMUNITY && isPermittedAccessor(accessor)) {
            return new CommunityArticle(title, content, accessor.getMemberId(), this);
        }

        if (type == ArticleType.NOTICE && isOwner(accessor)) {
            return new NoticeArticle(title, content, accessor.getMemberId(), this);
        }

        throw new NotParticipatedMemberException();
    }

    public Review writeReview(final Accessor accessor, final String content) {
        if (!isPermittedAccessor(accessor)) {
            throw new NotParticipatedMemberException();
        }
        return new Review(new AssociatedStudy(studyId), new Reviewer(accessor.getMemberId()), content);
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
