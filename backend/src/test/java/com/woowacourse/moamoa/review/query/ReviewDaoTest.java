package com.woowacourse.moamoa.review.query;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.리액트_리뷰1;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.리액트_리뷰1_내용;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.리액트_리뷰2;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.리액트_리뷰2_내용;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.리액트_리뷰3;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.리액트_리뷰3_내용;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.자바_리뷰1;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.자바_리뷰1_내용;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.자바_리뷰2;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.자바_리뷰2_내용;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.자바_리뷰3;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.자바_리뷰3_내용;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.자바_리뷰4;
import static com.woowacourse.moamoa.fixtures.ReviewFixtures.자바_리뷰4_내용;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디_신청서;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_신청서;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.domain.Review;
import com.woowacourse.moamoa.review.domain.repository.ReviewRepository;
import com.woowacourse.moamoa.review.query.data.ReviewData;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class ReviewDaoTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReviewDao sut;

    private Study javaStudy;

    private Study reactStudy;

    private List<ReviewData> javaReviews;

    private List<ReviewData> reactReviews;

    private Member 짱구;
    private Member 그린론;
    private Member 디우;
    private Member 베루스;

    @BeforeEach
    void setUp() {
        // 사용자 추가
        짱구 = memberRepository.save(짱구());
        그린론 = memberRepository.save(그린론());
        디우 = memberRepository.save(디우());
        베루스 = memberRepository.save(베루스());

        // 스터디 생성
        StudyService createStudyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        CreatingStudyRequest javaStudyRequest = 자바_스터디_신청서(startDate);
        CreatingStudyRequest reactStudyRequest = 리액트_스터디_신청서(startDate);

        javaStudy = createStudyService.createStudy(1L, javaStudyRequest);
        reactStudy = createStudyService.createStudy(1L, reactStudyRequest);

        // 리뷰 추가
        final Review firstJavaReview = reviewRepository.save(자바_리뷰1(javaStudy.getId(), 짱구.getId()));
        final Review secondJavaReview = reviewRepository.save(자바_리뷰2(javaStudy.getId(), 그린론.getId()));
        final Review thirdJavaReview = reviewRepository.save(자바_리뷰3(javaStudy.getId(), 디우.getId()));
        final Review forthJavaReview = reviewRepository.save(자바_리뷰4(javaStudy.getId(), 베루스.getId()));

        final Review firstReactReview = reviewRepository.save(리액트_리뷰1(reactStudy.getId(), 짱구.getId()));
        final Review secondReactReview = reviewRepository.save(리액트_리뷰2(reactStudy.getId(), 그린론.getId()));
        final Review thirdReactReview = reviewRepository.save(리액트_리뷰3(reactStudy.getId(), 디우.getId()));

        entityManager.flush();

        javaReviews = List.of(
                new ReviewData(forthJavaReview.getId(), new MemberData(짱구.getGithubId(), 짱구.getUsername(), 짱구.getImageUrl(), 짱구.getProfileUrl()),
                        firstJavaReview.getCreatedDate().toLocalDate(), firstJavaReview.getLastModifiedDate().toLocalDate(), 자바_리뷰1_내용),
                new ReviewData(thirdJavaReview.getId(), new MemberData(그린론.getGithubId(), 그린론.getUsername(), 그린론.getImageUrl(), 그린론.getProfileUrl()),
                        secondJavaReview.getCreatedDate().toLocalDate(), secondJavaReview.getLastModifiedDate().toLocalDate(), 자바_리뷰2_내용),
                new ReviewData(secondJavaReview.getId(), new MemberData(디우.getGithubId(), 디우.getUsername(), 디우.getImageUrl(), 디우.getProfileUrl()),
                        thirdJavaReview.getCreatedDate().toLocalDate(), thirdJavaReview.getLastModifiedDate().toLocalDate(), 자바_리뷰3_내용),
                new ReviewData(firstJavaReview.getId(), new MemberData(베루스.getGithubId(), 베루스.getUsername(), 베루스.getImageUrl(), 베루스.getProfileUrl()),
                        forthJavaReview.getCreatedDate().toLocalDate(), forthJavaReview.getLastModifiedDate().toLocalDate(), 자바_리뷰4_내용)
        );
        reactReviews = List.of(
                new ReviewData(firstReactReview.getId(), new MemberData(짱구.getGithubId(), 짱구.getUsername(), 짱구.getImageUrl(), 짱구.getProfileUrl()),
                        firstReactReview.getCreatedDate().toLocalDate(), firstReactReview.getLastModifiedDate().toLocalDate(), 리액트_리뷰1_내용),
                new ReviewData(secondReactReview.getId(), new MemberData(그린론.getGithubId(), 그린론.getUsername(), 그린론.getImageUrl(), 그린론.getProfileUrl()),
                        secondReactReview.getCreatedDate().toLocalDate(), secondReactReview.getLastModifiedDate().toLocalDate(), 리액트_리뷰2_내용),
                new ReviewData(thirdReactReview.getId(), new MemberData(디우.getGithubId(), 디우.getUsername(), 디우.getImageUrl(), 디우.getProfileUrl()),
                        thirdReactReview.getCreatedDate().toLocalDate(), thirdReactReview.getLastModifiedDate().toLocalDate(), 리액트_리뷰3_내용)
        );
    }

    @DisplayName("스터디 Id로 작성된 후기를 조회한다.")
    @Test
    void findAllReviewsByStudyId() {
        List<ReviewData> reviews = sut.findAllByStudyId(javaStudy.getId());

        assertThat(reviews).isNotEmpty();
        assertThat(reviews).hasSize(4)
                .filteredOn(review -> review.getId() != null)
                .extracting("member.githubId", "content")
                .containsExactlyInAnyOrder(
                        tuple(javaReviews.get(0).getMember().getGithubId(), javaReviews.get(0).getContent()),
                        tuple(javaReviews.get(1).getMember().getGithubId(), javaReviews.get(1).getContent()),
                        tuple(javaReviews.get(2).getMember().getGithubId(), javaReviews.get(2).getContent()),
                        tuple(javaReviews.get(3).getMember().getGithubId(), javaReviews.get(3).getContent())
                );
    }
}
