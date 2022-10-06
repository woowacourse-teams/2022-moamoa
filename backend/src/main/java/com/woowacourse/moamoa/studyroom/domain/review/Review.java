package com.woowacourse.moamoa.studyroom.domain.review;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableException;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE Review SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Review extends BaseEntity {

    private static final String TYPE_NAME = "REVIEW";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private AssociatedStudy associatedStudy;

    @Embedded
    private Reviewer reviewer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean deleted;

    public static Review write(final StudyRoom studyRoom, final Accessor accessor, final String content) {
        if (!studyRoom.isPermittedAccessor(accessor)) {
            throw new UnwritableException(studyRoom.getId(), accessor, TYPE_NAME);
        }
        return new Review(new AssociatedStudy(studyRoom.getId()), new Reviewer(accessor.getMemberId()), content);
    }

    public Review(
            final AssociatedStudy associatedStudy, final Reviewer reviewer, final String content
    ) {
        this(null, associatedStudy, reviewer, content, false);
    }

    public void updateContent(final Accessor accessor, final String content) {
        if (isUneditableAccessor(accessor)) {
            throw new UneditableException(associatedStudy.getStudyId(), accessor, TYPE_NAME);
        }
        this.content = content;
    }

    public void delete(final Accessor accessor) {
        if (isUneditableAccessor(accessor)) {
            throw new UneditableException(associatedStudy.getStudyId(), accessor, TYPE_NAME);
        }
        deleted = true;
    }

    private boolean isUneditableAccessor(final Accessor accessor) {
        return !associatedStudy.isSameStudyId(accessor.getStudyId()) || !
                reviewer.isSameMemberId(accessor.getMemberId());
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
