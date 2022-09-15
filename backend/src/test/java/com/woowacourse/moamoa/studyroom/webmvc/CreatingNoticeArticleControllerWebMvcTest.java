package com.woowacourse.moamoa.studyroom.webmvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.studyroom.service.NoticeArticleService;
import com.woowacourse.moamoa.studyroom.service.request.CommunityArticleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class CreatingNoticeArticleControllerWebMvcTest extends WebMVCTest {

    @MockBean
    private NoticeArticleService noticeArticleService;

    @DisplayName("잘못된 토큰으로 커뮤니티 글을 생성할 경우 401을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "Bearer InvalidToken", "Invalid"})
    void unauthorizedByInvalidToken(String token) throws Exception {
        mockMvc.perform(
                post("/api/studies/{study-id}/community/articles", 1L)
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
                post("/api/studies/{study-id}/community/articles", "one")
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
                post("/api/studies/{study-id}/community/articles", "1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CommunityArticleRequest(title, "content")))
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
                post("/api/studies/{study-id}/community/articles", "1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommunityArticleRequest("title", content)))
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
                post("/api/studies/{study-id}/community/articles", "1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommunityArticleRequest(title, "content")))
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
                post("/api/studies/{study-id}/community/articles", "1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommunityArticleRequest("title", content)))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("제목은 30자 이하여야 한다.")
    @Test
    void badRequestByInvalidLengthTitle() throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                        post("/api/studies/{study-id}/community/articles", "1")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new CommunityArticleRequest("a".repeat(31), "cotent")))
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("내용은 5000자 이하여야 한다.")
    @Test
    void badRequestByInvalidLengthContent() throws Exception {
        final String token = "Bearer" + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(
                        post("/api/studies/{study-id}/community/articles", "1")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new CommunityArticleRequest("a".repeat(5001), "cotent")))
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
