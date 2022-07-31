package com.woowacourse.moamoa.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.review.service.SearchingReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = SearchingReviewController.class)
@Import({JwtTokenProvider.class})
@MockBean(JpaMetamodelMappingContext.class)
public class BadRequestReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchingReviewService searchingReviewService;

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
