package com.woowacourse.moamoa.review.service;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.domain.repository.ReviewRepository;
import com.woowacourse.moamoa.review.service.response.ReviewResponse;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class ReviewServiceTest {

    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        this.reviewService = new ReviewService(reviewRepository);
    }

    @DisplayName("Study로 Review들을 전체 조회할 수 있다.")
    @Test
    public void getReviewsByStudyWithSize() {
        final ReviewsResponse reviewsResponse = reviewService.getReviewsByStudy(1L, null);

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
        final ReviewsResponse reviewsResponse = reviewService.getReviewsByStudy(1L, 6);

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
}
