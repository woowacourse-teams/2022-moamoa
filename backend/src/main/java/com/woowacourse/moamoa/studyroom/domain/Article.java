package com.woowacourse.moamoa.studyroom.domain;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.domain.MemberRole;
import com.woowacourse.moamoa.study.domain.Study;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    public Article(final Long id, final Long authorId, final Study study) {
        this.id = id;
        this.authorId = authorId;
        this.study = study;
    }

    public boolean isViewableBy(final Accessor accessor) {
        return isSameStudy(accessor) && study.isParticipant(accessor.getMemberId());
    }

    public boolean isEditableBy(final Accessor accessor) {
        return isSameStudy(accessor) && isAuthor(accessor);
    }

    private boolean isSameStudy(final Accessor accessor) {
        return study.getId().equals(accessor.getStudyId());
    }

    private boolean isAuthor(final Accessor accessor) {
        return this.authorId.equals(accessor.getMemberId());
    }

    public abstract void update(Accessor accessor, String title, String content);

    public static Article write(final Long memberId, final Study study,
                                final String title, final String content, final ArticleType type) {
        final MemberRole role = study.getRole(memberId);

        if (type == ArticleType.COMMUNITY && !role.equals(MemberRole.NON_MEMBER)) {
            return new CommunityArticle(title, content, memberId, study);
        }

        if (type == ArticleType.NOTICE && role.equals(MemberRole.OWNER)) {
            return new NoticeArticle(title, content, memberId, study);
        }

        throw new NotParticipatedMemberException();
    }
}
