package com.woowacourse.moamoa.comment.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.comment.service.exception.UnwrittenCommentException;
import com.woowacourse.moamoa.common.entity.BaseEntity;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Author author;

    @Embedded
    private AssociatedCommunity associatedCommunity;

    private String content;

    public Comment(final Author author, final AssociatedCommunity associatedCommunity, final String content) {
        this.author = author;
        this.associatedCommunity = associatedCommunity;
        this.content = content;
    }

    public void updateContent(final Author author, final String content) {
        validateAuthor(author);
        this.content = content;
    }

    public void validateAuthor(final Author author) {
        if (!this.author.equals(author)) {
            throw new UnwrittenCommentException();
        }
    }
}