package com.woowacourse.moamoa.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.moamoa.comment.service.exception.UnwrittenCommentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    @DisplayName("동일한 Author 이면 댓글을 수정할 수 있다.")
    @Test
    void updateContent() {
        // given
        final Author author = new Author(1L);
        final AssociatedCommunity associatedCommunity = new AssociatedCommunity(1L);
        final Comment comment = new Comment(author, associatedCommunity, "댓글 내용");

        // when
        comment.updateContent(author, "수정된 댓글 내용");

        // then
        assertThat(comment.getContent()).isEqualTo("수정된 댓글 내용");
    }

    @DisplayName("댓글을 작성한 작성자인지를 검사할 수 있다.")
    @Test
    void validateAuthor() {
        // given
        final Author author = new Author(1L);
        final AssociatedCommunity associatedCommunity = new AssociatedCommunity(1L);
        final Comment comment = new Comment(author, associatedCommunity, "댓글 내용");

        // when
        final Author otherAuthor = new Author(2L);

        // then
        assertDoesNotThrow(() -> comment.validateAuthor(author));
        assertThatThrownBy(() -> comment.validateAuthor(otherAuthor))
                .isInstanceOf(UnwrittenCommentException.class);
    }
}
