package com.woowacourse.moamoa.community.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.WebMVCTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;

public class GettingCommunityArticleControllerWebMvcTest extends WebMVCTest {

    @DisplayName("잘못된 토큰으로 커뮤니티 글을 생성할 경우 401을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "Bearer InvalidToken", "Invalid"})
    void unauthorizedByInvalidToken(String token) throws Exception {
        mockMvc.perform(
                get("/api/studies/{study-id}/community/articles/{article-id}", 1L, 1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
        )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @DisplayName("스터디 ID 또는 게시글 ID가 잘못된 형식인 경우 400에러를 반환한다.")
    @ParameterizedTest
    @CsvSource({"one, 1", "1, one"})
    void badRequestByInvalidIdFormat(String studyId, String articleId) throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L);

        mockMvc.perform(
                get("/api/studies/{study-id}/community/articles/{article-id}", studyId, articleId)
                        .header(HttpHeaders.AUTHORIZATION, token)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
