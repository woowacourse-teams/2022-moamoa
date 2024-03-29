package com.woowacourse.moamoa.studyroom.webmvc.review;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.common.WebMVCTest;
import com.woowacourse.moamoa.studyroom.service.request.ReviewRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class UnauthorizedReviewWebMvcTest extends WebMVCTest {

    @DisplayName("유효하지 않은 토큰으로 리뷰 작성하려는 경우 401 에러을 반환한다.")
    @Test
    void requestByInvalidToken() throws Exception {
        final String invalidToken = "JJANGGUInvalidToken";
        final String content = objectMapper.writeValueAsString(new ReviewRequest("짱구"));

        mockMvc.perform(post("/api/studies/1/reviews")
                        .header(HttpHeaders.AUTHORIZATION, invalidToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
