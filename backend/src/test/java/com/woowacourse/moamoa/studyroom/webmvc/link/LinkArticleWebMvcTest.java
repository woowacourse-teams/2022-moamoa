package com.woowacourse.moamoa.studyroom.webmvc.link;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.common.WebMVCTest;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class LinkArticleWebMvcTest extends WebMVCTest {

    @DisplayName("필수 데이터인 링크 URL이 null인 경우 400을 반환한다.")
    @Test
    void requestByNullLinkUrl() throws Exception {
        final String token = "Bearer " + tokenProvider.createToken(1L).getAccessToken();
        final String content = objectMapper.writeValueAsString(new LinkArticleRequest(null, "설명"));

        mockMvc.perform(post("/api/studies/1/reference-room/links")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("필수 데이터인 링크 URL이 공백인 경우 400을 반환한다.")
    @Test
    void requestByBlankLinkUrl() throws Exception {
        final String token = "Bearer " + tokenProvider.createToken(1L).getAccessToken();
        final String content = objectMapper.writeValueAsString(new LinkArticleRequest("", "설명"));

        mockMvc.perform(post("/api/studies/1/reference-room/links")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("링크 공유 설명이 40글자 이상인 경우 400을 반환한다.")
    @Test
    void requestBy40LengthExceededDescription() throws Exception {
        final String token = "Bearer " + tokenProvider.createToken(1L).getAccessToken();
        final String content = objectMapper.writeValueAsString(new LinkArticleRequest("링크",
                "일이삼사오육칠팔구십"
                        + "일이삼사오육칠팔이십"
                        + "일이삼사오육칠팔삼십"
                        + "일이삼사오육칠팔사십"
                        + "일이삼사오육앗싸오십일"));

        mockMvc.perform(post("/api/studies/1/reference-room/links")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("정상적이지 않은 스터디 id인 경우 400을 반환한다.")
    @Test
    void requestByInvalidStudyId() throws Exception {
        final String token = "Bearer " + tokenProvider.createToken(1L).getAccessToken();
        final String content = objectMapper.writeValueAsString(new LinkArticleRequest("링크", "설명"));

        mockMvc.perform(post("/api/studies/one/reference-room/links")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("page 파라미터 형식이 잘못된 경우 400을 반환한다.")
    @Test
    void requestByInvalidPageParameter() throws Exception {
        final String token = tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(get("/api/studies/1/reference-room/links")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("page", "one"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("size 파라미터 형식이 잘못된 경우 400을 반환한다.")
    @Test
    void requestByInvalidSizeParameter() throws Exception {
        final String token = tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(get("/api/studies/1/reference-room/links")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("size", "one"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("유효하지 않은 토큰으로 호출하는 경우 401을 반환한다.")
    @Test
    void requestByInvalidToken() throws Exception {
        final String invalidToken = "Bearer Invalid Token";
        final String content = objectMapper.writeValueAsString(new LinkArticleRequest("링크", "설명"));

        mockMvc.perform(post("/api/studies/one/reference-room/links")
                        .header(HttpHeaders.AUTHORIZATION, invalidToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
