package com.woowacourse.moamoa.review.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.query.ReviewDao;
import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.request.SizeRequest;
import com.woowacourse.moamoa.review.service.response.ReviewResponse;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import com.woowacourse.moamoa.review.service.response.WriterResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
class ReviewControllerTest {

    private static final MemberData JJANGGU = new MemberData(1L, "jjanggu", "https://image", "github.com");
    private static final MemberData GREENLAWN = new MemberData(2L, "greenlawn", "https://image", "github.com");
    private static final MemberData DWOO = new MemberData(3L, "dwoo", "https://image", "github.com");
    private static final MemberData VERUS = new MemberData(4L, "verus", "https://image", "github.com");

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReviewDao reviewDao;

    private ReviewController sut;

    private Study javaStudy;

    private List<ReviewResponse> javaReviews;

    @BeforeEach
    void setUp() {
        sut = new ReviewController(new ReviewService(reviewDao));

        // 사용자 추가
        final Member jjanggu = memberRepository.save(toMember(JJANGGU));
        final Member greenlawn = memberRepository.save(toMember(GREENLAWN));
        final Member dwoo = memberRepository.save(toMember(DWOO));
        final Member verus = memberRepository.save(toMember(VERUS));

        // 스터디 생성
        StudyService studyService = new StudyService(studyRepository, memberRepository);

        final LocalDate startDate = LocalDate.now();
        CreatingStudyRequest javaStudyRequest = CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();
        CreatingStudyRequest reactStudyRequest = CreatingStudyRequest.builder()
                .title("react 스터디").excerpt("리액트 설명").thumbnail("react image").description("리액트 소개")
                .startDate(startDate)
                .build();

        javaStudy = studyService.createStudy(1L, javaStudyRequest);
        final Study reactStudy = studyService.createStudy(1L, reactStudyRequest);

        final LocalDate createdAt = startDate.plusDays(1);
        final LocalDate lastModifiedDate = startDate.plusDays(2);

        entityManager.flush();
        entityManager.clear();

        // 리뷰 추가
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (1, " + javaStudy.getId() + ", " + jjanggu.getId() + ", '리뷰 내용1', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (2, " + javaStudy.getId() + ", " + greenlawn.getId() + ", '리뷰 내용2', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (3, " + javaStudy.getId() + ", " + dwoo.getId()+ ", '리뷰 내용3', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (4, " + javaStudy.getId() + ", " + verus.getId() + ", '리뷰 내용4', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (5, " + reactStudy.getId() + ", " + jjanggu.getId() + ", '리뷰 내용5', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (6, " + reactStudy.getId() + ", " + greenlawn.getId()+ ", '리뷰 내용6', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (7, " + reactStudy.getId() + ", " + dwoo.getId()+ ", '리뷰 내용7', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");

        javaReviews = List.of(
                new ReviewResponse(1L, new WriterResponse(JJANGGU), createdAt, lastModifiedDate, "리뷰 내용1"),
                new ReviewResponse(2L, new WriterResponse(GREENLAWN), createdAt, lastModifiedDate, "리뷰 내용2"),
                new ReviewResponse(3L, new WriterResponse(DWOO), createdAt, lastModifiedDate, "리뷰 내용3"),
                new ReviewResponse(4L, new WriterResponse(VERUS), createdAt, lastModifiedDate, "리뷰 내용4")
        );
    }

    private static Member toMember(MemberData memberData) {
        return new Member(memberData.getGithubId(), memberData.getUsername(), memberData.getImageUrl(),
                memberData.getProfileUrl());
    }

    @DisplayName("스터디의 전체 후기를 조회할 수 있다.")
    @Test
    public void getAllReviews() {
        final ResponseEntity<ReviewsResponse> reviewsResponse = sut.getReviews(javaStudy.getId(), SizeRequest.empty());

        assertThat(reviewsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reviewsResponse.getBody()).isNotNull();
        assertThat(reviewsResponse.getBody().getTotalResults()).isEqualTo(4);
        assertThat(reviewsResponse.getBody().getReviews()).containsExactlyInAnyOrderElementsOf(javaReviews);
    }

    @DisplayName("원하는 갯수 만큼 스터디의 후기를 조회할 수 있다.")
    @Test
    public void getReviewsByStudy() {
        final ResponseEntity<ReviewsResponse> reviewsResponse = sut.getReviews(javaStudy.getId(), new SizeRequest(2));

        assertThat(reviewsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reviewsResponse.getBody()).isNotNull();
        assertThat(reviewsResponse.getBody().getTotalResults()).isEqualTo(4);
        assertThat(reviewsResponse.getBody().getReviews()).containsExactlyInAnyOrderElementsOf(javaReviews.subList(0, 2));
    }

    @DisplayName("원하는 갯수보다 후기가 적은 경우 나머지 후기를 조회한다.")
    @Test
    void getRemainReviews() {
        final ResponseEntity<ReviewsResponse> reviewsResponse = sut.getReviews(javaStudy.getId(), new SizeRequest(5));

        assertThat(reviewsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reviewsResponse.getBody()).isNotNull();
        assertThat(reviewsResponse.getBody().getTotalResults()).isEqualTo(4);
        assertThat(reviewsResponse.getBody().getReviews()).containsExactlyInAnyOrderElementsOf(javaReviews);
    }
}
