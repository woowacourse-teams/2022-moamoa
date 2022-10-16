package com.woowacourse.moamoa.studyroom.webmvc.temparticle;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.common.WebMVCTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;

public class GettingTempArticleControllerWebMvcTest extends WebMVCTest {

    @DisplayName("토큰없이 임시글을 조회할 경우 401을 반환한다.")
    @Test
    void unauthorizedByEmptyToken() throws Exception {
        mockMvc.perform(
                get("/api/studies/{study-id}/notice/draft-articles/{article-id}", 1L, 1L)
        )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @DisplayName("잘못된 토큰으로 임시글을 조회할 경우 401을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "Bearer InvalidToken", "Invalid"})
    void unauthorizedByInvalidToken(String token) throws Exception {
        mockMvc.perform(
                get("/api/studies/{study-id}/notice/draft-articles/{article-id}", 1L, 1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
        )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @DisplayName("스터디 ID가 잘못된 형식인 경우 400에러를 반환한다.")
    @Test
    void badRequestByInvalidStudyIdFormat() throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                get("/api/studies/{study-id}/notice/draft-articles/{article-id}", "one", "1")
                        .header(AUTHORIZATION, token)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("게시글 ID가 잘못된 형식인 경우 400에러를 반환한다.")
    @Test
    void badRequestByInvalidArticleIdFormat() throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                get("/api/studies/{study-id}/notice/draft-articles/{article-id}", "1", "one")
                        .header(AUTHORIZATION, token)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
