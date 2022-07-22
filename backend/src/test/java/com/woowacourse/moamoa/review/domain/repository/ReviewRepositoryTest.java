package com.woowacourse.moamoa.review.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import com.woowacourse.moamoa.review.domain.Review;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

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
