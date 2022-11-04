package com.woowacourse.moamoa.studyroom.webmvc.temparticle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.common.WebMVCTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;

class DeletingTempArticleControllerWebMvcTest extends WebMVCTest {

    @DisplayName("토큰없이 임시글을 조회할 경우 401을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"notice", "community"})
    void unauthorizedByEmptyToken(final String type) throws Exception {
        mockMvc.perform(
                delete("/api/studies/{study-id}/{article-type}/draft-articles/{article-id}", 1L, type, 1L)
        )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @DisplayName("빈 문자열 토큰으로 커뮤니티 글을 생성할 경우 401을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"notice", "community"})
    void unauthorizedByBlankToken(String type) throws Exception {
        mockMvc.perform(
                delete("/api/studies/{study-id}/{article-type}/draft-articles/{article-id}", 1L, type, 1L)
                        .header(HttpHeaders.AUTHORIZATION, "")
        )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }


    @DisplayName("잘못된 토큰으로 커뮤니티 글을 생성할 경우 401을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"Bearer InvalidToken, notice", "Invalid, notice",
            "Bearer InvalidToken, community", "Invalid, community"})
    void unauthorizedByInvalidToken(String token, String type) throws Exception {
        mockMvc.perform(
                delete("/api/studies/{study-id}/{type}/draft-articles/{article-id}", 1L, type, 1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
        )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @DisplayName("스터디 ID 또는 게시글 ID가 잘못된 형식인 경우 400에러를 반환한다.")
    @ParameterizedTest
    @CsvSource({"one, 1, notice", "1, one, notice", "one, 1, community", "1, one, community"})
    void badRequestByInvalidIdFormat(String studyId, String articleId, String type) throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                delete("/api/studies/{study-id}/{type}/draft-articles/{article-id}", studyId, type, articleId)
                        .header(HttpHeaders.AUTHORIZATION, token)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
