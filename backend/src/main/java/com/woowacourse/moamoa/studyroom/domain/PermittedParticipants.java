package com.woowacourse.moamoa.studyroom.domain;

import com.woowacourse.moamoa.common.entity.ReadOnlyCollectionPersister;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Persister;

@Entity
@Table(name = "study")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PermittedParticipants {

    @Id
    @Column(name = "id", updatable = false, insertable = false)
    private Long studyId;

    @Column(name = "owner_id", nullable = false, updatable = false, insertable = false)
    private Long ownerId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "study_member",
            joinColumns = @JoinColumn(name = "study_id", updatable = false, insertable = false)
    )
    @Column(name = "member_id", updatable = false, insertable = false)
    @Persister(impl = ReadOnlyCollectionPersister.class)
    private Set<Long> participants;

    public PermittedParticipants(Long studyId, Long ownerId, Set<Long> participants) {
        this.studyId = studyId;
        this.ownerId = ownerId;
        this.participants = participants;
    }

    boolean isOwner(final Accessor accessor) {
        return studyId.equals(accessor.getStudyId()) && ownerId.equals(accessor.getMemberId());
    }

    public boolean isPermittedAccessor(final Accessor accessor) {
        return studyId.equals(accessor.getStudyId()) &&
                (isOwner(accessor) || participants.contains(accessor.getMemberId()));
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PermittedParticipants that = (PermittedParticipants) o;
        return Objects.equals(studyId, that.studyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studyId);
    }

}
