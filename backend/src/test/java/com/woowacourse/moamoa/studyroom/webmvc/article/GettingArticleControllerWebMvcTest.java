package com.woowacourse.moamoa.studyroom.webmvc.article;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.common.WebMVCTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GettingArticleControllerWebMvcTest extends WebMVCTest {

    @DisplayName("스터디 ID가 잘못된 형식인 경우 400에러를 반환한다.")
    @Test
    void badRequestByInvalidIdFormat() throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                        get("/api/studies/{study-id}/community/articles", "one")
                                .header(AUTHORIZATION, token)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("잘못된 형식의 파라미터를 전달한 경우 400 에러를 반환한다.")
    @ParameterizedTest
    @CsvSource({"one,1", "1,one", "-1,3", "1,-1", "1,0"})
    void badRequestByInvalidFormatParam(String page, String size) throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                get("/api/studies/{study-id}/community/articles", "1")
                        .header(AUTHORIZATION, token)
                        .param("page", page)
                        .param("size", size)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("페이지 정보 없이 게시글 목록 조회 시 400 에러를 반환한다.")
    @Test
    void badRequestByEmptyPage() throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                get("/api/studies/{study-id}/community/articles", "1")
                        .header(AUTHORIZATION, token)
                        .param("page", "")
                        .param("size", "5")
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
