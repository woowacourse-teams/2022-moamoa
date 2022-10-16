package com.woowacourse.moamoa.comment.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.comment.service.exception.UnwrittenCommentException;
import com.woowacourse.moamoa.common.entity.BaseEntity;
import javax.persistence.Column;
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

    @Column(name = "article_id", nullable = false)
    private Long articleId;

    private String content;

    public Comment(final Author author, final Long articleId, final String content) {
        this.author = author;
        this.articleId = articleId;
        this.content = content;
    }

    public void updateContent(final Author author, final String content) {
        if (!isAuthor(author)) {
            throw new UnwrittenCommentException();
        }
        this.content = content;
    }

    public boolean isAuthor(final Author author) {
        return this.author.equals(author);
    }
}
