package com.woowacourse.moamoa.comment.service;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.moamoa.comment.domain.Author;
import com.woowacourse.moamoa.comment.repository.CommentRepository;
import com.woowacourse.moamoa.comment.service.request.CommentRequest;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.studyroom.domain.CommunityArticle;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepository;
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
    private ArticleRepository<CommunityArticle> communityRepository;

    @Autowired
    private MyStudyDao myStudyDao;

    @Autowired
    private EntityManager entityManager;

    private CommentService sut;

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
        sut = new CommentService(commentRepository, myStudyDao);

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

        entityManager.flush();
    }

    @DisplayName("스터디에 참여한 사람은 게시판에 댓글을 작성할 수 있다.")
    @Test
    void writeComment() {
        // given
        final CommentRequest request = new CommentRequest("댓글 내용");

        // when & then
        assertDoesNotThrow(
                () -> sut.writeComment(베루스.getId(), 자바스크립트_스터디.getId(), 자바스크립트_스터디_게시판.getId(), request)
        );
    }

    @DisplayName("스터디원이 아닌 사람은 스터디 게시판에 댓글을 작성할 수 없다.")
    @Test
    void invalidAuthor() {
        // given
        final Author author = new Author(짱구.getId());
        final CommentRequest request = new CommentRequest("댓글 내용");

        // when & then
        assertThatThrownBy(
                () -> sut.writeComment(author.getMemberId(), 자바스크립트_스터디.getId(), 자바스크립트_스터디_게시판.getId(), request)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
