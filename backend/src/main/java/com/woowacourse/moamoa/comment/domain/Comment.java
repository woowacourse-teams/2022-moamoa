package com.woowacourse.moamoa.comment.domain;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.comment.service.exception.UnEditingCommentException;
import com.woowacourse.moamoa.comment.service.exception.UnWrittenCommentException;
import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Author author;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    private String content;

    private Comment(final Author author, final Article article, final String content) {
        this.author = author;
        this.article = article;
        this.content = content;
    }

    public static Comment write(final Accessor accessor, final Article article, final String content) {
        if (article.isSigningUp(accessor)) {
            return new Comment(new Author(accessor.getMemberId()), article, content);
        }
        throw new UnWrittenCommentException();
    }

    public void updateContent(final Accessor accessor, final String content) {
        if (isUneditableAccessor(accessor)) {
            throw new UnEditingCommentException();
        }
        this.content = content;
    }

    public boolean isUneditableAccessor(final Accessor accessor) {
        return !author.getAuthorId().equals(accessor.getMemberId()) || !article.isSigningUp(accessor);
    }
}
