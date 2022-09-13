package com.woowacourse.moamoa.studyroom.domain.article;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "study_id")
    private StudyRoom studyRoom;

    public Article(final Long id, final Long authorId, StudyRoom studyRoom) {
        this.id = id;
        this.authorId = authorId;
        this.studyRoom = studyRoom;
    }

    protected final boolean isPermittedAccessor(final Accessor accessor) {
        return studyRoom.isPermittedAccessor(accessor);
    }

    protected final boolean isAuthor(final Accessor accessor) {
        return this.authorId.equals(accessor.getMemberId());
    }

    protected final boolean isOwner(final Accessor accessor) {
        return studyRoom.isOwner(accessor);
    }

    public abstract void update(Accessor accessor, String title, String content);

    public abstract boolean isEditableBy(final Accessor accessor);

    public abstract boolean isViewableBy(final Accessor accessor);
}
