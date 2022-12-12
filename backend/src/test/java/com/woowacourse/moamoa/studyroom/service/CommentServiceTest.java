package com.woowacourse.moamoa.studyroom.service;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디;
import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.COMMUNITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.moamoa.studyroom.domain.comment.Author;
import com.woowacourse.moamoa.studyroom.domain.comment.Comment;
import com.woowacourse.moamoa.studyroom.domain.comment.repository.CommentRepository;
import com.woowacourse.moamoa.studyroom.query.CommentDao;
import com.woowacourse.moamoa.studyroom.service.exception.UnEditingCommentException;
import com.woowacourse.moamoa.studyroom.service.exception.UnWrittenCommentException;
import com.woowacourse.moamoa.studyroom.service.request.CommentRequest;
import com.woowacourse.moamoa.studyroom.service.request.EditingCommentRequest;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyParticipantService;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import com.woowacourse.moamoa.studyroom.domain.article.repository.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import com.woowacourse.moamoa.studyroom.service.CommentService;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class CommentServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private EntityManager entityManager;

    private StudyParticipantService studyService;

    private CommentService sut;

    private Member 짱구;
    private Member 그린론;
    private Member 디우;
    private Member 베루스;

    private Study 자바스크립트_스터디;

    private Article 자바스크립트_스터디_게시판;

    @BeforeEach
    void setUp() {
        studyService = new StudyParticipantService(memberRepository, studyRepository, new DateTimeSystem());
        sut = new CommentService(commentRepository, articleRepository, commentDao);

        짱구 = memberRepository.save(짱구());
        그린론 = memberRepository.save(그린론());
        디우 = memberRepository.save(디우());
        베루스 = memberRepository.save(베루스());

        자바스크립트_스터디 = studyRepository.save(자바스크립트_스터디(그린론.getId(), Set.of(디우.getId(), 베루스.getId())));

        final StudyRoom javaStudyRoom = new StudyRoom(자바스크립트_스터디.getId(), 자바스크립트_스터디.getParticipants().getOwnerId(),
                Set.of(디우.getId(), 베루스.getId()));
        final Accessor accessor = new Accessor(디우.getId(), 자바스크립트_스터디.getId());
        final Content content = new Content("게시판 제목", "게시판 내용");
        final Article article = Article.create(javaStudyRoom, accessor, content, COMMUNITY);

        자바스크립트_스터디_게시판 = articleRepository.save(article);

        final Accessor 그린론_자바스크립트_접근자 = new Accessor(그린론.getId(), 자바스크립트_스터디.getId());
        final Accessor 디우_자바스크립트_접근자 = new Accessor(디우.getId(), 자바스크립트_스터디.getId());
        final Accessor 베루스_자바스크립트_접근자 = new Accessor(베루스.getId(), 자바스크립트_스터디.getId());

        final Comment 첫번째_댓글 = Comment.write(그린론_자바스크립트_접근자, article, "댓글 내용1");
        final Comment 두번째_댓글 = Comment.write(디우_자바스크립트_접근자, article, "댓글 내용2");
        final Comment 세번째_댓글 = Comment.write(베루스_자바스크립트_접근자, article, "댓글 내용3");

        commentRepository.save(첫번째_댓글);
        commentRepository.save(두번째_댓글);
        commentRepository.save(세번째_댓글);

        entityManager.flush();
    }

    @DisplayName("스터디에 참여한 사람은 게시판에 댓글을 작성할 수 있다.")
    @Test
    void writeComment() {
        // given
        final CommentRequest request = new CommentRequest("댓글 내용");

        // when & then
        assertDoesNotThrow(
                () -> sut.writeComment(베루스.getId(), 자바스크립트_스터디.getId(), 자바스크립트_스터디_게시판.getId(), COMMUNITY, request)
        );
    }

    @DisplayName("스터디원이 아닌 사람은 스터디 게시판에 댓글을 작성할 수 없다.")
    @Test
    void invalidAuthor() {
        // given
        final Author author = new Author(짱구.getId());
        final CommentRequest request = new CommentRequest("댓글 내용");

        // when & then
        final Long authorId = author.getAuthorId();
        final Long studyId = 자바스크립트_스터디.getId();
        final Long articleId = 자바스크립트_스터디_게시판.getId();
        assertThatThrownBy(
                () -> sut.writeComment(authorId, studyId, articleId, COMMUNITY, request)
        ).isInstanceOf(UnWrittenCommentException.class);
    }

    @DisplayName("내가 작성한 댓글은 수정할 수 있다.")
    @Test
    void updateComment() {
        // given
        final Long studyId = 자바스크립트_스터디.getId();
        final Author author = new Author(디우.getId());
        final CommentRequest request = new CommentRequest("댓글 내용");

        final Long commentId = sut.writeComment(author.getAuthorId(), studyId, 자바스크립트_스터디_게시판.getId(),
                COMMUNITY, request);

        // when
        sut.update(디우.getId(), studyId, commentId, new EditingCommentRequest("수정된 댓글 내용"));

        // then
        final Comment comment = commentRepository.findById(commentId).get();
        assertThat(comment.getContent()).isEqualTo("수정된 댓글 내용");
    }

    @DisplayName("본인이 작성하지 않은 댓글은 수정할 수 없다.")
    @Test
    void canNotUpdateOthersComment() {
        // given
        final Long studyId = 자바스크립트_스터디.getId();
        final Author author = new Author(디우.getId());
        final CommentRequest request = new CommentRequest("댓글 내용");

        final Long commentId = sut.writeComment(author.getAuthorId(), studyId, 자바스크립트_스터디_게시판.getId(),
                COMMUNITY, request);

        // when & then
        final Long 베루스_ID = 베루스.getId();
        final EditingCommentRequest editingCommentRequest = new EditingCommentRequest("수정된 댓글 내용");
        assertThatThrownBy(
                () -> sut.update(베루스_ID, studyId, commentId, editingCommentRequest)
        ).isInstanceOf(UnEditingCommentException.class);
    }

    @DisplayName("본인이 작성한 댓글은 삭제할 수 있다.")
    @Test
    void delete() {
        // given
        final Long studyId = 자바스크립트_스터디.getId();
        final Author author = new Author(디우.getId());
        final CommentRequest request = new CommentRequest("댓글 내용");

        final Long commentId = sut.writeComment(author.getAuthorId(), studyId, 자바스크립트_스터디_게시판.getId(),
                COMMUNITY, request);

        // when
        sut.delete(디우.getId(), studyId, commentId);

        // then
        final Optional<Comment> comment = commentRepository.findById(commentId);
        assertTrue(comment.isEmpty());
    }

    @DisplayName("본인이 작성하지 않은 댓글은 삭제할 수 없다.")
    @Test
    void canNotDeleteOthersComment() {
        // given
        final Long studyId = 자바스크립트_스터디.getId();
        final Author author = new Author(디우.getId());
        final CommentRequest request = new CommentRequest("댓글 내용");

        final Long commentId = sut.writeComment(author.getAuthorId(), studyId, 자바스크립트_스터디_게시판.getId(),
                COMMUNITY, request);

        // when & then
        final Long 베루스_ID = 베루스.getId();
        assertThatThrownBy(
                () -> sut.delete(베루스_ID, studyId, commentId)
        ).isInstanceOf(UnEditingCommentException.class);
    }

    @DisplayName("탈퇴한 회원은 본인의 댓글을 수정할 수 없다.")
    @Test
    void updateWhenLeaveStudy() {
        // given
        final Long 디우_ID = 디우.getId();
        final Long studyId = 자바스크립트_스터디.getId();
        final Author author = new Author(디우_ID);
        final CommentRequest request = new CommentRequest("댓글 내용");

        final Long commentId = sut.writeComment(author.getAuthorId(), studyId, 자바스크립트_스터디_게시판.getId(),
                COMMUNITY, request);

        studyService.leaveStudy(디우_ID, studyId);
        entityManager.flush();
        entityManager.clear();

        // when & then
        final EditingCommentRequest editingCommentRequest = new EditingCommentRequest("수정된 댓글 내용");
        assertThatThrownBy(() ->
                sut.update(디우_ID, studyId, commentId, editingCommentRequest)
        ).isInstanceOf(UnEditingCommentException.class);
    }

    @DisplayName("탈퇴한 회원은 본인의 댓글을 삭제할 수 없다.")
    @Test
    void deleteWhenLeaveStudy() {
        // given
        final Long 디우_ID = 디우.getId();
        final Long studyId = 자바스크립트_스터디.getId();
        final Author author = new Author(디우_ID);
        final CommentRequest request = new CommentRequest("댓글 내용");

        final Long commentId = sut.writeComment(author.getAuthorId(), studyId, 자바스크립트_스터디_게시판.getId(),
                COMMUNITY, request);

        studyService.leaveStudy(디우_ID, studyId);
        entityManager.flush();
        entityManager.clear();

        // when & then
        assertThatThrownBy(() -> sut.delete(디우_ID, studyId, commentId))
                .isInstanceOf(UnEditingCommentException.class);
    }
}
