package com.woowacourse.moamoa.community.domain;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.domain.Study;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CommunityArticle {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "study_id")
    private Long studyId;

    private CommunityArticle(final String title, final String content, final Long authorId, final Long studyId) {
        this(null, title, content, authorId, studyId);
    }

    public static CommunityArticle write(final Member member, final Study study, final ArticleRequest request) {
        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        return new CommunityArticle(request.getTitle(), request.getContent(), member.getId(), study.getId());
    }
}
