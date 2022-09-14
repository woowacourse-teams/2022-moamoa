package com.woowacourse.moamoa.comment.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.http.HttpStatus.CREATED;

import com.woowacourse.moamoa.comment.domain.AssociatedCommunity;
import com.woowacourse.moamoa.comment.domain.Author;
import com.woowacourse.moamoa.comment.domain.Comment;
import com.woowacourse.moamoa.comment.domain.repository.CommentRepository;
import com.woowacourse.moamoa.comment.query.CommentDao;
import com.woowacourse.moamoa.comment.service.CommentService;
import com.woowacourse.moamoa.comment.service.request.CommentRequest;
import com.woowacourse.moamoa.comment.service.request.EditingCommentRequest;
import com.woowacourse.moamoa.comment.service.response.CommentsResponse;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.studyroom.domain.CommunityArticle;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepository;
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
    private ArticleRepository<CommunityArticle> communityRepository;

    @Autowired
    private MyStudyDao myStudyDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private EntityManager entityManager;

    private CommentService commentService;

    private StudyService studyService;

    private CommentController sut;

    private Member 짱구;
    private Member 그린론;
    private Member 디우;
    private Member 베루스;

    private Study 자바_스터디;
    private Study 리액트_스터디;
    private Study 자바스크립트_스터디;

    private CommunityArticle 자바스크립트_스터디_게시판;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        commentService = new CommentService(commentRepository, memberRepository, myStudyDao, commentDao);
        sut = new CommentController(commentService);

        짱구 = memberRepository.save(짱구());
        그린론 = memberRepository.save(그린론());
        디우 = memberRepository.save(디우());
        베루스 = memberRepository.save(베루스());

        자바_스터디 = studyRepository.save(자바_스터디(짱구.getId(), Set.of(그린론.getId(), 디우.getId())));
        리액트_스터디 = studyRepository.save(리액트_스터디(디우.getId(), Set.of(짱구.getId(), 그린론.getId(), 베루스.getId())));
        자바스크립트_스터디 = studyRepository.save(자바스크립트_스터디(그린론.getId(), Set.of(디우.getId(), 베루스.getId())));

        final CommunityArticle communityArticle = new CommunityArticle("게시판 제목", "게시판 내용", 짱구.getId(),
                new StudyRoom(자바스크립트_스터디.getId(), 그린론.getId(), Set.of(디우.getId(), 베루스.getId())));

        자바스크립트_스터디_게시판 = communityRepository.save(communityArticle);

        final AssociatedCommunity associatedCommunity = new AssociatedCommunity(자바스크립트_스터디_게시판.getId());
        final Comment 첫번째_댓글 = new Comment(new Author(그린론.getId()), associatedCommunity, "댓글 내용1");
        final Comment 두번째_댓글 = new Comment(new Author(디우.getId()), associatedCommunity, "댓글 내용2");
        final Comment 세번째_댓글 = new Comment(new Author(베루스.getId()), associatedCommunity, "댓글 내용3");

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
        ResponseEntity<Void> response = sut.createComment(그린론.getId(), 자바스크립트_스터디.getId(),
                자바스크립트_스터디_게시판.getId(), request);

        // then
        String location = response.getHeaders().getLocation().getPath();
        Long commentId = Long.valueOf(location.replaceAll("/api/studies/\\d+/community/articles/\\d+/comments/", ""));

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(location).matches("/api/studies/\\d+/community/articles/\\d+/comments/\\d+");
        assertThat(commentId).isNotNull();
    }

    @DisplayName("원하는 갯수 만큼 게시판의 댓글을 조회할 수 있다.")
    @Test
    void getReviewsByStudy() {
        final ResponseEntity<CommentsResponse> response = sut.getComments(자바스크립트_스터디_게시판.getId(), Pageable.ofSize(2));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getComments())
                .hasSize(2)
                .filteredOn(it -> it.getId() != null)
                .extracting("author.githubId", "content")
                .contains(
                        tuple(디우.getGithubId(), "댓글 내용2"),
                        tuple(베루스.getGithubId(), "댓글 내용3")
                );
    }

    @DisplayName("본인이 작성한 댓글은 수정 가능하다.")
    @Test
    void updateComment() {
        // given
        final CommentRequest request = new CommentRequest("댓글 내용");
        ResponseEntity<Void> response = sut.createComment(그린론.getId(), 자바스크립트_스터디.getId(),
                자바스크립트_스터디_게시판.getId(), request);

        String location = response.getHeaders().getLocation().getPath();
        Long commentId = Long.valueOf(location.replaceAll("/api/studies/\\d+/community/articles/\\d+/comments/", ""));

        // when
        final ResponseEntity<Void> updatedResponse = sut.updateComment(그린론.getId(), commentId,
                new EditingCommentRequest("수정된 댓글 내용"));

        // then
        assertThat(updatedResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
