package com.woowacourse.moamoa.review.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import com.woowacourse.moamoa.review.domain.Review;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

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

        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 1, '리뷰 내용1', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 2, '리뷰 내용2', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 3, '리뷰 내용3', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 4, '리뷰 내용4', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 1, '리뷰 내용5', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 2, '리뷰 내용6', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 3, '리뷰 내용7', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
    }

    @DisplayName("스터디에 후기 목록을 조회한다.")
    @Test
    public void findReviewsByStudy() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(1L);
        final List<Review> reviews = reviewRepository.findAllByAssociatedStudy(associatedStudy);

        assertThat(reviews).hasSize(7)
                .extracting("content")
                .contains("리뷰 내용1", "리뷰 내용2", "리뷰 내용3", "리뷰 내용4", "리뷰 내용5", "리뷰 내용6", "리뷰 내용7");
    }

    @DisplayName("스터디에서 조회한 리뷰로부터 Member를 조회한다.")
    @Test
    public void findMemberFromReview() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(1L);
        final List<Review> reviews = reviewRepository.findAllByAssociatedStudy(associatedStudy);
        final Member member = reviews.get(0).getMember();

        assertThat(member).extracting("githubId", "username", "imageUrl", "profileUrl")
                .containsExactly(1L, "jjanggu", "https://image", "github.com");
    }
}