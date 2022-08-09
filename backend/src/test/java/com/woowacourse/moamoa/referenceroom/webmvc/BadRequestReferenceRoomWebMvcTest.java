package com.woowacourse.moamoa.referenceroom.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.referenceroom.service.request.CreatingLinkRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class BadRequestReferenceRoomWebMvcTest extends WebMVCTest {

    @DisplayName("필수 데이터인 링크 URL이 null인 경우 400을 반환한다.")
    @Test
    void requestByNullLinkUrl() throws Exception {
        final String token = "Bearer " + tokenProvider.createToken(1L);
        final String content = objectMapper.writeValueAsString(new CreatingLinkRequest(null, "설명"));

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
        final String token = "Bearer " + tokenProvider.createToken(1L);
        final String content = objectMapper.writeValueAsString(new CreatingLinkRequest("링크", "설명"));

        mockMvc.perform(post("/api/studies/one/reference-room/links")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
