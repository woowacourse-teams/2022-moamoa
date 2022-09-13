package com.woowacourse.moamoa.studyroom.domain;

import com.woowacourse.moamoa.common.entity.ReadOnlyCollectionPersister;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Persister;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PermittedParticipants {

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

    public PermittedParticipants(final Long ownerId, final Set<Long> participants) {
        this.ownerId = ownerId;
        this.participants = participants;
    }

    public boolean isOwner(final Accessor accessor) {
        return ownerId.equals(accessor.getMemberId());
    }

    public boolean isPermittedAccessor(final Accessor accessor) {
        return isOwner(accessor) || participants.contains(accessor.getMemberId());
    }
}
