package com.woowacourse.moamoa.studyroom.domain;

import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeArticle;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeContent;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "study")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRoom {

    @Id
    @Column(name = "id", updatable = false, insertable = false)
    private Long studyId;

    @Embedded
    private PermittedParticipants permittedParticipants;

    public StudyRoom(Long studyId, Long ownerId, Set<Long> participants) {
        this.studyId = studyId;
        this.permittedParticipants = new PermittedParticipants(ownerId, participants);
    }

    public boolean isOwner(final Accessor accessor) {
        return studyId.equals(accessor.getStudyId()) && permittedParticipants.isOwner(accessor);
    }

    public boolean isPermittedAccessor(final Accessor accessor) {
        return studyId.equals(accessor.getStudyId()) && permittedParticipants.isPermittedAccessor(accessor);
    }

    public Long getId() {
        return studyId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudyRoom that = (StudyRoom) o;
        return Objects.equals(studyId, that.studyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studyId);
    }
}
