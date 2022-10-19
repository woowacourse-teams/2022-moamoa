package com.woowacourse.moamoa.comment.domain;

import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.COMMUNITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    private static final Article ARTICLE = Article.create(
            new StudyRoom(1L, 2L, Set.of(1L, 2L)),
            new Accessor(1L, 1L),
            new Content("글 제목", "글 내용"),
            COMMUNITY);

    @DisplayName("동일한 Author 이고 스터디에 가입중이면 댓글을 수정할 수 있다.")
    @Test
    void updateContent() {
        // given
        final Author author = new Author(1L);
        final Comment comment = new Comment(author, ARTICLE, "댓글 내용");
        final Accessor accessor = new Accessor(author.getAuthorId(), 1L);

        // when
        comment.updateContent(accessor, "수정된 댓글 내용");

        // then
        assertThat(comment.getContent()).isEqualTo("수정된 댓글 내용");
    }

    @DisplayName("동일한 Author 이고 스터디에 가입중이면 댓글을 삭제할 수 있다.")
    @Test
    void deleteContent() {
        // given
        final Author author = new Author(1L);
        final Comment comment = new Comment(author, ARTICLE, "댓글 내용");
        final Accessor accessor = new Accessor(author.getAuthorId(), 1L);

        // when & then
        assertDoesNotThrow(() -> comment.isUneditableAccessor(accessor));
    }
}
