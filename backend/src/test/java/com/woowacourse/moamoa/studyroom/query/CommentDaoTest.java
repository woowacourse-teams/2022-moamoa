package com.woowacourse.moamoa.studyroom.query;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디;
import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.COMMUNITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.studyroom.domain.comment.Comment;
import com.woowacourse.moamoa.studyroom.domain.comment.repository.CommentRepository;
import com.woowacourse.moamoa.studyroom.query.data.CommentData;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import com.woowacourse.moamoa.studyroom.domain.article.repository.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import com.woowacourse.moamoa.studyroom.query.CommentDao;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

@RepositoryTest
class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    private Member 짱구;
    private Member 그린론;
    private Member 디우;
    private Member 베루스;

    private Study 자바스크립트_스터디;

    private Article 자바스크립트_스터디_게시판;

    @BeforeEach
    void setUp() {
        짱구 = memberRepository.save(짱구());
        그린론 = memberRepository.save(그린론());
        디우 = memberRepository.save(디우());
        베루스 = memberRepository.save(베루스());

        자바스크립트_스터디 = studyRepository.save(자바스크립트_스터디(그린론.getId(), Set.of(디우.getId(), 베루스.getId())));

        final StudyRoom javaStudyRoom = new StudyRoom(자바스크립트_스터디.getId(), 자바스크립트_스터디.getParticipants().getOwnerId(),
                Set.of(그린론.getId(), 디우.getId()));
        final Accessor accessor = new Accessor(디우.getId(), 자바스크립트_스터디.getId());
        final Content content = new Content("게시판 제목", "게시판 내용");
        final Article article = Article.create(javaStudyRoom, accessor, content, COMMUNITY);

        자바스크립트_스터디_게시판 = articleRepository.save(article);

        entityManager.flush();
    }

    @DisplayName("게시글에 작성된 댓글들을 조회할 수 있다.")
    @Test
    void findAllByArticleId() {
        // given
        final Article article = 자바스크립트_스터디_게시판;
        final Accessor 그린론_자바스크립트_접근자 = new Accessor(그린론.getId(), 자바스크립트_스터디.getId());
        final Accessor 디우_자바스크립트_접근자 = new Accessor(디우.getId(), 자바스크립트_스터디.getId());
        final Comment firstComment = Comment.write(그린론_자바스크립트_접근자, article, "댓글내용1");
        final Comment secondComment = Comment.write(디우_자바스크립트_접근자, article, "댓글내용2");

        final Comment savedFirstComment = commentRepository.save(firstComment);
        final Comment savedSecondComment = commentRepository.save(secondComment);

        entityManager.flush();

        // when
        final List<CommentData> commentData = commentDao.findAllByArticleId(article.getId(), Pageable.ofSize(8))
                .getContent();

        // then
        assertThat(commentData).hasSize(2)
                .extracting("id", "member.id", "content")
                .contains(
                        tuple(savedFirstComment.getId(), 그린론.getId(), "댓글내용1"),
                        tuple(savedSecondComment.getId(), 디우.getId(), "댓글내용2")
                );
    }
}
