package com.woowacourse.moamoa.review.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.review.controller.ReviewController;
import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ReviewController.class)
@Import({JwtTokenProvider.class})
@MockBean(JpaMetamodelMappingContext.class)
public class BadRequestReviewWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @DisplayName("필수 데이터인 후기 내용이 공백인 경우 400을 반환한다.")
    @Test
    void requestByBlankContent() throws Exception {
        final String token = "Bearer " + tokenProvider.createToken(1L);
        final String content = objectMapper.writeValueAsString(new WriteReviewRequest(""));

        mockMvc.perform(post("/api/studies/1/reviews")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("필수 데이터인 후기 내용이 null 값인 경우 400을 반환한다.")
    @Test
    void requestByEmptyContent() throws Exception {
        final String token = "Bearer " + tokenProvider.createToken(1L);

        mockMvc.perform(post("/api/studies/1/reviews")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
