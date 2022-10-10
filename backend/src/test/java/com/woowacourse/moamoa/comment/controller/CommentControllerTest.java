package com.woowacourse.moamoa.comment.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디;
import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.COMMUNITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.http.HttpStatus.CREATED;

import com.woowacourse.moamoa.comment.domain.Author;
import com.woowacourse.moamoa.comment.domain.Comment;
import com.woowacourse.moamoa.comment.domain.repository.CommentRepository;
import com.woowacourse.moamoa.comment.query.CommentDao;
import com.woowacourse.moamoa.comment.service.CommentService;
import com.woowacourse.moamoa.comment.service.request.CommentRequest;
import com.woowacourse.moamoa.comment.service.request.EditingCommentRequest;
import com.woowacourse.moamoa.comment.service.response.CommentsResponse;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import com.woowacourse.moamoa.studyroom.domain.article.repository.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
class CommentControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MyStudyDao myStudyDao;

    @Autowired
    private EntityManager entityManager;

    private CommentService commentService;

    private CommentController sut;

    private Member 짱구;
    private Member 그린론;
    private Member 디우;
    private Member 베루스;

    private Study 자바_스터디;

    private Article 자바_스터디_게시판;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository, articleRepository, commentDao, myStudyDao);
        sut = new CommentController(commentService);

        짱구 = memberRepository.save(짱구());
        그린론 = memberRepository.save(그린론());
        디우 = memberRepository.save(디우());
        베루스 = memberRepository.save(베루스());

        자바_스터디 = studyRepository.save(자바_스터디(짱구.getId(), Set.of(그린론.getId(), 디우.getId())));

        final StudyRoom javaStudyRoom = new StudyRoom(자바_스터디.getId(), 자바_스터디.getParticipants().getOwnerId(),
                Set.of(그린론.getId(), 디우.getId()));
        final Accessor accessor = new Accessor(디우.getId(), 자바_스터디.getId());
        final Content content = new Content("게시판 제목", "게시판 내용");
        final Article article = Article.create(javaStudyRoom, accessor, content, COMMUNITY);

        자바_스터디_게시판 = articleRepository.save(article);

        final Comment 첫번째_댓글 = new Comment(new Author(그린론.getId()), article.getId(), "댓글 내용1");
        final Comment 두번째_댓글 = new Comment(new Author(디우.getId()), article.getId(), "댓글 내용2");
        final Comment 세번째_댓글 = new Comment(new Author(짱구.getId()), article.getId(), "댓글 내용3");

        commentRepository.save(첫번째_댓글);
        commentRepository.save(두번째_댓글);
        commentRepository.save(세번째_댓글);

        entityManager.flush();
    }

    @DisplayName("게시판에 댓글을 작성한다.")
    @Test
    void writeComment() {
        // given
        final CommentRequest request = new CommentRequest("댓글 내용");

        // when
        final ResponseEntity<Void> response = sut.createComment(그린론.getId(), 자바_스터디.getId(), 자바_스터디_게시판.getId(),
                COMMUNITY.getTypeName(), request);

        // then
        String location = response.getHeaders().getLocation().getPath();
        Long commentId = Long.valueOf(location.replaceAll("/api/studies/\\d+/community/\\d+/comments/", ""));

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(location).matches("/api/studies/\\d+/community/\\d+/comments/\\d+");
        assertThat(commentId).isNotNull();
    }

    @DisplayName("원하는 갯수 만큼 게시판의 댓글을 조회할 수 있다.")
    @Test
    void getReviewsByStudy() {
        final ResponseEntity<CommentsResponse> response = sut.getComments(자바_스터디_게시판.getId(), Pageable.ofSize(2));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().getComments())
                .hasSize(2)
                .filteredOn(it -> it.getId() != null)
                .extracting("author.memberId", "content")
                .containsExactlyInAnyOrder(
                        tuple(디우.getId(), "댓글 내용2"),
                        tuple(짱구.getId(), "댓글 내용3")
                );
    }

    @DisplayName("본인이 작성한 댓글은 수정 가능하다.")
    @Test
    void updateComment() {
        // given
        final CommentRequest request = new CommentRequest("댓글 내용");
        final ResponseEntity<Void> response = sut.createComment(디우.getId(), 자바_스터디.getId(), 자바_스터디_게시판.getId(),
                COMMUNITY.getTypeName(), request);

        String location = response.getHeaders().getLocation().getPath();
        Long commentId = Long.valueOf(location.replaceAll("/api/studies/\\d+/community/\\d+/comments/", ""));

        // when
        final ResponseEntity<Void> updatedResponse = sut.updateComment(디우.getId(), 자바_스터디.getId(), commentId,
                new EditingCommentRequest("수정된 댓글 내용"));

        // then
        assertThat(updatedResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @DisplayName("본인이 작성한 댓글은 삭제가 가능하다.")
    @Test
    void deleteComment() {
        // given
        final CommentRequest request = new CommentRequest("댓글 내용");
        final ResponseEntity<Void> response = sut.createComment(디우.getId(), 자바_스터디.getId(), 자바_스터디_게시판.getId(),
                COMMUNITY.getTypeName(), request);

        String location = response.getHeaders().getLocation().getPath();
        Long commentId = Long.valueOf(location.replaceAll("/api/studies/\\d+/community/\\d+/comments/", ""));

        // when
        final ResponseEntity<Void> updatedResponse = sut.deleteComment(디우.getId(), 자바_스터디.getId(), commentId);

        // then
        assertThat(updatedResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
