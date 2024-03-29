package com.woowacourse.moamoa.studyroom.webmvc.temparticle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.common.WebMVCTest;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class UpdatingTempArticleControllerWebMvcTest extends WebMVCTest {

    @DisplayName("토큰없이 임시글을 수정할 경우 401을 반환한다.")
    @Test
    void unauthorizedByEmptyToken() throws Exception {
        mockMvc.perform(
                put("/api/studies/{study-id}/notice/draft-articles/{article-id}", 1L, 1L)
        )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @DisplayName("잘못된 토큰으로 커뮤니티 글을 수정할 경우 401을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "Bearer InvalidToken", "Invalid"})
    void unauthorizedByInvalidToken(String token) throws Exception {
        mockMvc.perform(
                put("/api/studies/{study-id}/notice/draft-articles/{article-id}", 1L, 1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
        )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @DisplayName("스터디 ID 또는 게시글 ID가 잘못된 형식인 경우 400에러를 반환한다.")
    @ParameterizedTest
    @CsvSource({"one, 1", "1, one"})
    void badRequestByInvalidIdFormat(String studyId, String articleId) throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                put("/api/studies/{study-id}/notice/draft-articles/{article-id}", studyId, articleId)
                        .header(HttpHeaders.AUTHORIZATION, token)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("제목은 null 또는 Empty 일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void badRequestByNullOrEmptyTitle(String title) throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                put("/api/studies/{study-id}/notice/draft-articles/{article-id}", "1", "1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ArticleRequest(title, "content")))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("내용은 null 또는 Empty 일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void badRequestByNullOrEmptyContent(String content) throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                put("/api/studies/{study-id}/notice/draft-articles/{article-id}", "1", "1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ArticleRequest("title", content)))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("제목은 공백일 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"   ", "\t", "\n"})
    void badRequestByBlankTitle(String title) throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                put("/api/studies/{study-id}/notice/draft-articles/{article-id}", "1", "1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ArticleRequest(title, "content")))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("내용은 공백일 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"   ", "\t", "\n"})
    void badRequestByBlankContent(String content) throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                put("/api/studies/{study-id}/notice/draft-articles/{article-id}", "1", "1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ArticleRequest("title", content)))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("제목은 30자 이하여야 한다.")
    @Test
    void badRequestByInvalidLengthTitle() throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                put("/api/studies/{study-id}/notice/draft-articles/{article-id}", "1", "1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ArticleRequest("a".repeat(31), "cotent")))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("내용은 5000자 이하여야 한다.")
    @Test
    void badRequestByInvalidLengthContent() throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                put("/api/studies/{study-id}/notice/draft-articles/{article-id}", "1", "1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ArticleRequest("a".repeat(5001), "cotent")))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
