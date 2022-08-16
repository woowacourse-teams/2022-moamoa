package com.woowacourse.moamoa.review.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class BadRequestReviewWebMvcTest extends WebMVCTest {

    @DisplayName("필수 데이터인 후기 내용이 공백인 경우 400을 반환한다.")
    @Test
    void requestByBlankContent() throws Exception {
        final String token = "Bearer " + tokenProvider.createToken(1L).getAccessToken();
        final String content = objectMapper.writeValueAsString(new WriteReviewRequest(""));

        mockMvc.perform(post("/api/studies/1/reviews")
                        .cookie(new Cookie(ACCESS_TOKEN, token))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("필수 데이터인 후기 내용이 null 값인 경우 400을 반환한다.")
    @Test
    void requestByEmptyContent() throws Exception {
        final String token = "Bearer " + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(post("/api/studies/1/reviews")
                        .cookie(new Cookie(ACCESS_TOKEN, token))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("size 파라미터의 형식이 잘못된 경우 400을 반환한다.")
    @Test
    void requestByInvalidSize() throws Exception {
        mockMvc.perform(get("/api/studies/1/reviews")
                .param("size", "one"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("study-id의 형식이 잘못된 경우 400을 반환한다.")
    @Test
    void requestByInvalidStudyId() throws Exception {
        mockMvc.perform(get("/api/studies/one/reviews")
                .param("size", "5"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
