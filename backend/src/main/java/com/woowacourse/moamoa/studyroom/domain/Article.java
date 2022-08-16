package com.woowacourse.moamoa.studyroom.domain;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
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

    public boolean isViewableBy(final Accessor accessor) {
        return studyRoom.isPermittedAccessor(accessor);
    }

    public boolean isEditableBy(final Accessor accessor) {
        return studyRoom.isPermittedAccessor(accessor) && isAuthor(accessor);
    }

    private boolean isAuthor(final Accessor accessor) {
        return this.authorId.equals(accessor.getMemberId());
    }

    public abstract void update(Accessor accessor, String title, String content);

    public Article write(final Accessor accessor, final StudyRoom studyRoom, final String title, final String content, final ArticleType type) {
        if (type == ArticleType.COMMUNITY && studyRoom.isPermittedAccessor(accessor)) {
            return new CommunityArticle(title, content, accessor.getMemberId(), studyRoom);
        }

        if (type == ArticleType.NOTICE && studyRoom.isOwner(accessor)) {
            return new NoticeArticle(title, content, accessor.getMemberId(), studyRoom);
        }

        throw new NotParticipatedMemberException();
    }
}
