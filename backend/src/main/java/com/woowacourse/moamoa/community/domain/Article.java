package com.woowacourse.moamoa.community.domain;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.domain.MemberRole;
import com.woowacourse.moamoa.study.domain.Study;
import java.util.Objects;
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

    private String title;

    private String content;

    @Column(name = "author_id")
    private Long authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    Article(final String title, final String content, final Long authorId, final Study study) {
        this(null, title, content, authorId, study);
    }

    public Article(final Long id, final String title, final String content, final Long authorId,
                            final Study study) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.study = study;
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public boolean isViewableBy(final Long studyId, final Long memberId) {
        return isBelongTo(studyId) && study.isParticipant(memberId);
    }

    public boolean isEditableBy(final Long studyId, final Long memberId) {
        return isViewableBy(studyId, memberId) && isAuthor(memberId);
    }

    private boolean isBelongTo(final Long studyId) {
        return this.study.getId().equals(studyId);
    }

    private boolean isAuthor(final Long memberId) {
        return this.authorId.equals(memberId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommunityArticle that = (CommunityArticle) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle())
                && Objects.equals(getContent(), that.getContent()) && Objects.equals(getAuthorId(),
                that.getAuthorId()) && Objects.equals(getStudy().getId(), that.getStudy().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContent(), getAuthorId(), getStudy().getId());
    }

    public static Article write(final Member member, final Study study, final ArticleRequest request,
                                  final String articleType) {
        final MemberRole role = study.getRole(member.getId());

        if (articleType.equals("community")) {
            if (role.equals(MemberRole.NON_MEMBER)) {
                throw new NotParticipatedMemberException();
            }
            return new CommunityArticle(request.getTitle(), request.getContent(), member.getId(), study);
        }

        if (role.equals(MemberRole.OWNER)) {
            return new NoticeArticle(request.getTitle(), request.getContent(), member.getId(), study);
        }

        throw new NotParticipatedMemberException();
    }
}
