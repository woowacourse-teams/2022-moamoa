package com.woowacourse.moamoa.studyroom.query.review;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_응답;
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

import com.woowacourse.moamoa.alarm.service.alarmsender.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.service.alarmuserclient.SlackUsersClient;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import com.woowacourse.moamoa.studyroom.domain.review.repository.ReviewRepository;
import com.woowacourse.moamoa.studyroom.domain.review.Review;
import com.woowacourse.moamoa.studyroom.query.ReviewDao;
import com.woowacourse.moamoa.studyroom.query.data.ReviewData;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@RepositoryTest
@Import({RestTemplate.class, SlackAlarmSender.class, SlackUsersClient.class})
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

    private List<ReviewData> javaReviews;

    @BeforeEach
    void setUp() {
        // 사용자 추가
        final Long 짱구_아이디 = memberRepository.save(짱구()).getId();
        final Long 그린론_아이디 = memberRepository.save(그린론()).getId();
        final Long 디우_아이디 = memberRepository.save(디우()).getId();
        final Long 베루스_아이디 = memberRepository.save(베루스()).getId();

        // 스터디 생성
        StudyService createStudyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        StudyRequest javaStudyRequest = 자바_스터디_신청서(startDate);
        StudyRequest reactStudyRequest = 리액트_스터디_신청서(startDate);

        javaStudy = createStudyService.createStudy(짱구_아이디, javaStudyRequest);

        // 리뷰 추가
        final Review firstJavaReview = reviewRepository.save(자바_리뷰1(javaStudy.getId(), 짱구_아이디));
        final Review secondJavaReview = reviewRepository.save(자바_리뷰2(javaStudy.getId(), 그린론_아이디));
        final Review thirdJavaReview = reviewRepository.save(자바_리뷰3(javaStudy.getId(), 디우_아이디));
        final Review forthJavaReview = reviewRepository.save(자바_리뷰4(javaStudy.getId(), 베루스_아이디));

        entityManager.flush();

        javaReviews = List.of(
                new ReviewData(forthJavaReview.getId(), 짱구_응답(짱구_아이디),
                        firstJavaReview.getCreatedDate().toLocalDate(), firstJavaReview.getLastModifiedDate().toLocalDate(), 자바_리뷰1_내용),
                new ReviewData(thirdJavaReview.getId(), 그린론_응답(그린론_아이디),
                        secondJavaReview.getCreatedDate().toLocalDate(), secondJavaReview.getLastModifiedDate().toLocalDate(), 자바_리뷰2_내용),
                new ReviewData(secondJavaReview.getId(), 디우_응답(디우_아이디),
                        thirdJavaReview.getCreatedDate().toLocalDate(), thirdJavaReview.getLastModifiedDate().toLocalDate(), 자바_리뷰3_내용),
                new ReviewData(firstJavaReview.getId(), 베루스_응답(베루스_아이디),
                        forthJavaReview.getCreatedDate().toLocalDate(), forthJavaReview.getLastModifiedDate().toLocalDate(), 자바_리뷰4_내용)
        );
    }

    @DisplayName("스터디 Id로 작성된 후기를 조회한다.")
    @Test
    void findAllReviewsByStudyId() {
        List<ReviewData> reviews = sut.findAllByStudyId(javaStudy.getId());

        assertThat(reviews).isNotEmpty();
        assertThat(reviews).hasSize(4)
                .filteredOn(review -> review.getId() != null)
                .extracting("member.id", "content")
                .containsExactlyInAnyOrder(
                        tuple(javaReviews.get(0).getMember().getId(), javaReviews.get(0).getContent()),
                        tuple(javaReviews.get(1).getMember().getId(), javaReviews.get(1).getContent()),
                        tuple(javaReviews.get(2).getMember().getId(), javaReviews.get(2).getContent()),
                        tuple(javaReviews.get(3).getMember().getId(), javaReviews.get(3).getContent())
                );
    }
}
