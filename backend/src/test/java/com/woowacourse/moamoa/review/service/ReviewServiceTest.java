package com.woowacourse.moamoa.review.service;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.exception.BadRequestException;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.domain.repository.ReviewRepository;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.review.service.response.ReviewResponse;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
class ReviewServiceTest {

    private ReviewService reviewService;

    private SearchingReviewService searchingReviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initDataBase() {
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (1, 1, 'jjanggu', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (2, 2, 'greenlawn', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (3, 3, 'dwoo', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (4, 4, 'verus', 'https://image', 'github.com')");

        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, status, description, current_member_count, max_member_count, created_at, start_date, owner_id) VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'OPEN', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '2021-11-08T11:58:20.551705', '2021-12-08T11:58:20.657123', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, status, description, current_member_count, max_member_count, created_at, enrollment_end_date, start_date, end_date, owner_id) VALUES (2, 'React 스터디', '리액트 설명', 'react thumbnail', 'OPEN', '디우의 뤼액트 스터디입니다.', 4, 5, '2021-11-08T11:58:20.551705', '2021-11-09T11:58:20.551705', '2021-11-10T11:58:20.551705', '2021-12-08T11:58:20.551705', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, status, description, current_member_count, max_member_count, created_at, owner_id) VALUES (3, 'javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'OPEN', '그린론의 자바스크립트 접해보기', 3, 20, '2021-11-08T11:58:20.551705', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, status, description, max_member_count, created_at, owner_id) VALUES (4, 'HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'CLOSE', '디우의 HTTP 정복하기', 5, '2021-11-08T11:58:20.551705', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, status, description, current_member_count, created_at, owner_id, start_date) VALUES (5, '알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'CLOSE', '알고리즘을 TDD로 풀자의 베루스입니다.', 1, '2021-11-08T11:58:20.551705', 4, '2021-12-06T11:56:32.123567')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, status, description, current_member_count, created_at, owner_id, start_date, enrollment_end_date, end_date) VALUES (6, 'Linux 스터디', '리눅스 설명', 'linux thumbnail', 'CLOSE', 'Linux를 공부하자의 베루스입니다.', 1, '2021-11-08T11:58:20.551705', 4, '2021-12-06T11:56:32.123567', '2021-12-07T11:56:32.123567', '2022-01-07T11:56:32.123567')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, status, description, current_member_count, created_at, owner_id, start_date) VALUES (7, '짱구 스터디', '짱구 설명', 'jjanggu thumbnail', 'OPEN', '짱구입니다.', 1, '2021-11-08T11:58:20.551705', 4, '9999-12-31T11:56:32.123567')");


        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 1)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (7, 1)");

        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 1, '리뷰 내용1', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 2, '리뷰 내용2', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 3, '리뷰 내용3', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 4, '리뷰 내용4', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 1, '리뷰 내용5', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 2, '리뷰 내용6', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 3, '리뷰 내용7', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
    }

    @BeforeEach
    void setUp() {
        this.reviewService = new ReviewService(reviewRepository, memberRepository, studyRepository);
        this.searchingReviewService = new SearchingReviewService(reviewRepository);
    }

    @DisplayName("Study로 Review들을 전체 조회할 수 있다.")
    @Test
    public void getReviewsByStudyWithSize() {
        final ReviewsResponse reviewsResponse = searchingReviewService.getReviewsByStudy(1L, null);

        final Integer totalResults = reviewsResponse.getTotalResults();
        final List<ReviewResponse> reviews = reviewsResponse.getReviews();
        final List<MemberData> members = reviews.stream()
                .map(ReviewResponse::getMember)
                .collect(toList());

        assertThat(totalResults).isEqualTo(7);
        assertThat(reviews)
                .hasSize(7)
                .filteredOn(review -> review.getId() != null)
                .extracting("content", "createdDate", "lastModifiedDate")
                .contains(
                        tuple("리뷰 내용1", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용2", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용3", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용4", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용5", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용6", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용3", "2021-11-08", "2021-11-08")
                );
        assertThat(members).hasSize(7)
                .extracting("githubId", "username", "imageUrl", "profileUrl")
                .contains(
                        tuple(1L, "jjanggu", "https://image", "github.com"),
                        tuple(2L, "greenlawn", "https://image", "github.com"),
                        tuple(3L, "dwoo", "https://image", "github.com"),
                        tuple(4L, "verus", "https://image", "github.com"),
                        tuple(1L, "jjanggu", "https://image", "github.com"),
                        tuple(2L, "greenlawn", "https://image", "github.com"),
                        tuple(3L, "dwoo", "https://image", "github.com")
                );
    }

    @DisplayName("Study로 Review들을 조회할 수 있다.")
    @Test
    public void getReviewsByStudy() {
        final ReviewsResponse reviewsResponse = searchingReviewService.getReviewsByStudy(1L, 6);

        final Integer totalResults = reviewsResponse.getTotalResults();
        final List<ReviewResponse> reviews = reviewsResponse.getReviews();
        final List<MemberData> members = reviews.stream()
                .map(ReviewResponse::getMember)
                .collect(toList());

        assertThat(totalResults).isEqualTo(7);
        assertThat(reviews)
                .hasSize(6)
                .filteredOn(review -> review.getId() != null)
                .extracting("content", "createdDate", "lastModifiedDate")
                .contains(
                        tuple("리뷰 내용1", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용2", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용3", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용4", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용5", "2021-11-08", "2021-11-08"),
                        tuple("리뷰 내용6", "2021-11-08", "2021-11-08")

                );
        assertThat(members).hasSize(6)
                .extracting("githubId", "username", "imageUrl", "profileUrl")
                .contains(
                        tuple(1L, "jjanggu", "https://image", "github.com"),
                        tuple(2L, "greenlawn", "https://image", "github.com"),
                        tuple(3L, "dwoo", "https://image", "github.com"),
                        tuple(4L, "verus", "https://image", "github.com"),
                        tuple(1L, "jjanggu", "https://image", "github.com"),
                        tuple(2L, "greenlawn", "https://image", "github.com")
                );
    }

    @DisplayName("스터디가 시작하기 전에 후기를 작성하려 하면 예외를 반환한다.")
    @Test
    void createReviewBeforeTheStudyStarts() {
        final WriteReviewRequest writeReviewRequest = new WriteReviewRequest("content");

        // 7번 스터디의 시작일자는 9999년 12월 31일
        assertThatThrownBy(() -> reviewService.writeReview(1L, 7L, writeReviewRequest))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("스터디에 참여하지 않은 회원이 후기를 작성하려 하면 예외를 반환한다.")
    @Test
    void createReviewDidNotParticipateMember() {
        final WriteReviewRequest writeReviewRequest = new WriteReviewRequest("content");

        assertThatThrownBy(() -> reviewService.writeReview(2L, 1L, writeReviewRequest))
                .isInstanceOf(UnauthorizedException.class);
    }

    @DisplayName("진행중인 스터디에 참여한 회원은 정상적으로 후기를 작성할 수 있다.")
    @Test
    void writeReview() {
        final WriteReviewRequest writeReviewRequest = new WriteReviewRequest("content");

        assertDoesNotThrow(() -> reviewService.writeReview(1L, 1L, writeReviewRequest));
    }
}
