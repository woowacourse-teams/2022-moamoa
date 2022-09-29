package com.woowacourse.moamoa.studyroom.domain;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.comment.domain.AssociatedCommunity;
import com.woowacourse.moamoa.comment.domain.Author;
import com.woowacourse.moamoa.comment.domain.Comment;
import com.woowacourse.moamoa.comment.service.exception.UnwrittenCommentException;
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
    private StudyRoom studyRoom;

    Article(final Long id, final Long authorId, StudyRoom studyRoom) {
        this.id = id;
        this.authorId = authorId;
        this.studyRoom = studyRoom;
    }

    public Comment writeComment(final Accessor accessor, final String content) {
        if (studyRoom.isPermittedAccessor(accessor)) {
            return new Comment(new Author(accessor.getMemberId()), new AssociatedCommunity(id), content);
        }

        throw new UnwrittenCommentException();
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
