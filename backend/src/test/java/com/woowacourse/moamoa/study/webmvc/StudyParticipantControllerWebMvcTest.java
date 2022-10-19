package com.woowacourse.moamoa.study.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.common.WebMVCTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

public class StudyParticipantControllerWebMvcTest extends WebMVCTest {

    @DisplayName("스터디원 강퇴 시 정상적이지 않은 스터디 id인 경우 400을 반환한다.")
    @Test
    void requestByInvalidStudyId() throws Exception {
        final String 토큰 = "Bearer " + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(delete("/api/studies/one/members/2")
                .header(HttpHeaders.AUTHORIZATION, 토큰))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("스터디원 강퇴 시 정상적이지 않은 회원 id인 경우 400을 반환한다.")
    @Test
    void requestByInvalidMemberId() throws Exception {
        final String 토큰 = "Bearer " + tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(delete("/api/studies/1/members/two")
                        .header(HttpHeaders.AUTHORIZATION, 토큰))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
