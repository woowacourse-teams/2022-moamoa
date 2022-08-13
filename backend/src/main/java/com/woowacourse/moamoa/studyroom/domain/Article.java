package com.woowacourse.moamoa.studyroom.domain;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.common.entity.BaseEntity;
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
    private PermittedParticipants permittedParticipants;

    public Article(final Long id, final Long authorId, PermittedParticipants permittedParticipants) {
        this.id = id;
        this.authorId = authorId;
        this.permittedParticipants = permittedParticipants;
    }

    public boolean isViewableBy(final Accessor accessor) {
        return permittedParticipants.isPermittedAccessor(accessor);
    }

    public boolean isEditableBy(final Accessor accessor) {
        return permittedParticipants.isPermittedAccessor(accessor) && isAuthor(accessor);
    }

    private boolean isAuthor(final Accessor accessor) {
        return this.authorId.equals(accessor.getMemberId());
    }

    public abstract void update(Accessor accessor, String title, String content);
}
