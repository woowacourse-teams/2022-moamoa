package com.woowacourse.moamoa.referenceroom.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.referenceroom.service.exception.NotLinkAuthorException;
import com.woowacourse.moamoa.referenceroom.service.exception.NotRelatedLinkException;
import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LinkTest {

    @DisplayName("링크 공유를 수정한다.")
    @Test
    void update() {
        final Link link = new Link(new AssociatedStudy(1L), new Author(1L), "link", "설명");
        final Link updatedLink = new Link(new AssociatedStudy(1L), new Author(1L), "updated link", "수정된 설명");

        link.update(updatedLink);

        assertAll(
                () -> assertThat(link.getLinkUrl()).isEqualTo("updated link"),
                () -> assertThat(link.getDescription()).isEqualTo("수정된 설명")
        );
    }

    @DisplayName("작성자가 아니면 수정할 수 없다.")
    @Test
    void updateByNotAuthor() {
        final Author author = new Author(1L);
        final Author nonAuthor = new Author(2L);

        final Link link = new Link(new AssociatedStudy(1L), author, "link", "설명");
        final Link updatedLink = new Link(new AssociatedStudy(1L), nonAuthor, "updated link", "수정된 설명");

        assertThatThrownBy(() -> link.update(updatedLink))
                .isInstanceOf(NotLinkAuthorException.class);
    }

    @DisplayName("스터디에 속하지 않은 링크 공유글을 수정할 수 없다.")
    @Test
    void updateByNotBelongToStudy() {
        final AssociatedStudy relatedStudy = new AssociatedStudy(1L);
        final AssociatedStudy unrelatedStudy = new AssociatedStudy(2L);

        final Link link = new Link(relatedStudy, new Author(1L), "link", "설명");
        final Link updatedLink = new Link(unrelatedStudy, new Author(1L), "updated link", "수정된 설명");

        assertThatThrownBy(() -> link.update(updatedLink))
                .isInstanceOf(NotRelatedLinkException.class);
    }

    @DisplayName("링크 공유를 삭제한다.")
    @Test
    void delete() {
        final Author author = new Author(1L);
        final Link link = new Link(new AssociatedStudy(1L), author, "link", "설명");

        link.delete(new AssociatedStudy(1L), author);

        assertThat(link.isDeleted()).isTrue();
    }

    @DisplayName("작성자가 아니면 삭제할 수 없다.")
    @Test
    void deleteByNotAuthor() {
        final Link link = new Link(new AssociatedStudy(1L), new Author(1L), "link", "설명");
        final Author nonAuthor = new Author(2L);
        final AssociatedStudy associatedStudy = new AssociatedStudy(1L);

        assertThatThrownBy(() -> link.delete(associatedStudy, nonAuthor))
                .isInstanceOf(NotLinkAuthorException.class);
    }

    @DisplayName("스터디에 속하지 않은 링크 공유글을 삭제할 수 없다.")
    @Test
    void deleteByNotBelongToStudy() {
        final Link link = new Link(new AssociatedStudy(1L), new Author(1L), "link", "설명");
        final AssociatedStudy unrelatedStudy = new AssociatedStudy(2L);
        final Author author = new Author(1L);

        assertThatThrownBy(() -> link.delete(unrelatedStudy, author))
                .isInstanceOf(NotRelatedLinkException.class);
    }
}
