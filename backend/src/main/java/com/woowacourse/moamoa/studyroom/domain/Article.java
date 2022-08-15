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
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
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

    public boolean isViewableBy(final Accessor accessor) {
        return studyRoom.isPermittedAccessor(accessor);
    }

    public boolean isEditableBy(final Accessor accessor) {
        return studyRoom.isPermittedAccessor(accessor) && isAuthor(accessor);
    }

    private boolean isAuthor(final Accessor accessor) {
        return this.authorId.equals(accessor.getMemberId());
    }

    public Long getId() {
        return id;
    }

    final protected Long getAuthorId() {
        return authorId;
    }

    final protected StudyRoom getStudyRoom() {
        return studyRoom;
    }

    public abstract void update(Accessor accessor, String title, String content);

    public abstract void update(Accessor accessor, Content<?> content);
}
