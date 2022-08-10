package com.woowacourse.moamoa.review.query;

import static com.woowacourse.fixtures.MemberFixtures.그린론;
import static com.woowacourse.fixtures.MemberFixtures.그린론_깃허브_아이디;
import static com.woowacourse.fixtures.MemberFixtures.그린론_유저네임;
import static com.woowacourse.fixtures.MemberFixtures.그린론_이미지;
import static com.woowacourse.fixtures.MemberFixtures.그린론_프로필;
import static com.woowacourse.fixtures.MemberFixtures.디우;
import static com.woowacourse.fixtures.MemberFixtures.디우_깃허브_아이디;
import static com.woowacourse.fixtures.MemberFixtures.디우_유저네임;
import static com.woowacourse.fixtures.MemberFixtures.디우_이미지;
import static com.woowacourse.fixtures.MemberFixtures.디우_프로필;
import static com.woowacourse.fixtures.MemberFixtures.베루스;
import static com.woowacourse.fixtures.MemberFixtures.베루스_깃허브_아이디;
import static com.woowacourse.fixtures.MemberFixtures.베루스_유저네임;
import static com.woowacourse.fixtures.MemberFixtures.베루스_이미지;
import static com.woowacourse.fixtures.MemberFixtures.베루스_프로필;
import static com.woowacourse.fixtures.MemberFixtures.짱구;
import static com.woowacourse.fixtures.MemberFixtures.짱구_깃허브_아이디;
import static com.woowacourse.fixtures.MemberFixtures.짱구_유저네임;
import static com.woowacourse.fixtures.MemberFixtures.짱구_이미지;
import static com.woowacourse.fixtures.MemberFixtures.짱구_프로필;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.query.data.ReviewData;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
class ReviewDaoTest {

    private static final MemberData JJANGGU = new MemberData(짱구_깃허브_아이디, 짱구_유저네임, 짱구_이미지, 짱구_프로필);
    private static final MemberData GREENLAWN = new MemberData(그린론_깃허브_아이디, 그린론_유저네임, 그린론_이미지, 그린론_프로필);
    private static final MemberData DWOO = new MemberData(디우_깃허브_아이디, 디우_유저네임, 디우_이미지, 디우_프로필);
    private static final MemberData VERUS = new MemberData(베루스_깃허브_아이디, 베루스_유저네임, 베루스_이미지, 베루스_프로필);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReviewDao sut;

    private Study javaStudy;

    private Study reactStudy;

    private List<ReviewData> javaReviews;

    private List<ReviewData> reactReviews;

    @BeforeEach
    void setUp() {
        // 사용자 추가
        final Member jjanggu = memberRepository.save(짱구);
        final Member greenlawn = memberRepository.save(그린론);
        final Member dwoo = memberRepository.save(디우);
        final Member verus = memberRepository.save(베루스);

        // 스터디 생성
        StudyService createStudyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        CreatingStudyRequest javaStudyRequest = CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();
        CreatingStudyRequest reactStudyRequest = CreatingStudyRequest.builder()
                .title("react 스터디").excerpt("리액트 설명").thumbnail("react image").description("리액트 소개")
                .startDate(startDate)
                .build();

        javaStudy = createStudyService.createStudy(1L, javaStudyRequest);
        reactStudy = createStudyService.createStudy(1L, reactStudyRequest);

        final LocalDate createdDate = startDate.plusDays(1);
        final LocalDate lastModifiedDate = startDate.plusDays(2);

        entityManager.flush();
        entityManager.clear();

        // 리뷰 추가
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date, deleted) "
                + "VALUES (1, " + javaStudy.getId() + ", " + jjanggu.getId() + ", '리뷰 내용1', '"
                + createdDate + "T11:23:30.123456', '" + lastModifiedDate + "T11:45:20.456123', false)");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date, deleted) "
                + "VALUES (2, " + javaStudy.getId() + ", " + greenlawn.getId() + ", '리뷰 내용2', '"
                + createdDate + "T11:23:30.123456', '" + lastModifiedDate + "T11:45:20.456123', false)");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date, deleted) "
                + "VALUES (3, " + javaStudy.getId() + ", " + dwoo.getId()+ ", '리뷰 내용3', '"
                + createdDate + "T11:23:30.123456', '" + lastModifiedDate + "T11:45:20.456123', false)");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date, deleted) "
                + "VALUES (4, " + javaStudy.getId() + ", " + verus.getId() + ", '리뷰 내용4', '"
                + createdDate + "T11:23:30.123456', '" + lastModifiedDate + "T11:45:20.456123', false)");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date, deleted) "
                + "VALUES (5, " + reactStudy.getId() + ", " + jjanggu.getId() + ", '리뷰 내용5', '"
                + createdDate + "T11:23:30.123456', '" + lastModifiedDate + "T11:45:20.456123', false)");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date, deleted) "
                + "VALUES (6, " + reactStudy.getId() + ", " + greenlawn.getId()+ ", '리뷰 내용6', '"
                + createdDate + "T11:23:30.123456', '" + lastModifiedDate + "T11:45:20.456123', false)");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date, deleted) "
                + "VALUES (7, " + reactStudy.getId() + ", " + dwoo.getId()+ ", '리뷰 내용7', '"
                + createdDate + "T11:23:30.123456', '" + lastModifiedDate + "T11:45:20.456123', false)");

        javaReviews = List.of(
                new ReviewData(1L, JJANGGU, createdDate, lastModifiedDate, "리뷰 내용1"),
                new ReviewData(2L, GREENLAWN, createdDate, lastModifiedDate, "리뷰 내용2"),
                new ReviewData(3L, DWOO, createdDate, lastModifiedDate, "리뷰 내용3"),
                new ReviewData(4L, VERUS, createdDate, lastModifiedDate, "리뷰 내용4")
        );
        reactReviews = List.of(
                new ReviewData(5L, JJANGGU, createdDate, lastModifiedDate, "리뷰 내용5"),
                new ReviewData(6L, GREENLAWN, createdDate, lastModifiedDate, "리뷰 내용6"),
                new ReviewData(7L, DWOO, createdDate, lastModifiedDate, "리뷰 내용7")
        );
    }

    @DisplayName("스터디 Id로 작성된 후기를 조회한다.")
    @Test
    void findAllReviewsByStudyId() {
        List<ReviewData> reviews = sut.findAllByStudyId(javaStudy.getId());

        assertThat(reviews).isNotEmpty();
        assertThat(reviews).hasSize(4);
        assertThat(reviews).containsExactlyInAnyOrderElementsOf(javaReviews);
    }
}
