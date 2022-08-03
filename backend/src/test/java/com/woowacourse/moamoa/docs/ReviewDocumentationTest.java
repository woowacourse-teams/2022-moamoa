package com.woowacourse.moamoa.docs;

import static com.woowacourse.fixtures.AuthFixtures.JWT_토큰;
import static com.woowacourse.fixtures.ReviewFixtures.자바_리뷰1_데이터;
import static com.woowacourse.fixtures.ReviewFixtures.자바_리뷰1_아이디;
import static com.woowacourse.fixtures.ReviewFixtures.자바_리뷰2_데이터;
import static com.woowacourse.fixtures.ReviewFixtures.자바_리뷰3_데이터;
import static com.woowacourse.fixtures.ReviewFixtures.자바_리뷰4_데이터;
import static com.woowacourse.fixtures.ReviewFixtures.자바_리뷰_총_개수;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디_아이디;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ReviewDocumentationTest extends DocumentationTest {

    @DisplayName("리뷰를 작성하면 201을 반환한다.")
    @Test
    void create() throws Exception {
        final WriteReviewRequest request = new WriteReviewRequest("작성할 후기 내용입니다.");
        given(reviewService.writeReview(any(), any(), any())).willReturn(자바_리뷰1_아이디);

        mockMvc.perform(post("/api/studies/{study-id}/reviews", 자바_스터디_아이디)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_토큰)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(document("reviews/create"))
                .andExpect(status().isCreated());
    }

    @DisplayName("스터디에 리뷰를 전체 조회한다.")
    @Test
    void findAll() throws Exception {
        final ReviewsResponse reviewsResponse = new ReviewsResponse(List.of(자바_리뷰1_데이터, 자바_리뷰2_데이터, 자바_리뷰3_데이터, 자바_리뷰4_데이터), 자바_리뷰_총_개수);
        given(searchingReviewService.getReviewsByStudy(any(), any())).willReturn(reviewsResponse);

        mockMvc.perform(get("/api/studies/{study-id}/reviews", 자바_스터디_아이디)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("reviews/list"))
                .andExpect(status().isOk());
    }

    @DisplayName("스터디에 리뷰를 일정 개수만 조회한다.")
    @Test
    void findCertainNumber() throws Exception {
        final ReviewsResponse reviewsResponse = new ReviewsResponse(List.of(자바_리뷰1_데이터, 자바_리뷰2_데이터), 자바_리뷰_총_개수);
        given(searchingReviewService.getReviewsByStudy(any(), any())).willReturn(reviewsResponse);

        mockMvc.perform(get("/api/studies/{study-id}/reviews?size=2", 자바_스터디_아이디)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("reviews/list-certain-number"))
                .andExpect(status().isOk());
    }
}
