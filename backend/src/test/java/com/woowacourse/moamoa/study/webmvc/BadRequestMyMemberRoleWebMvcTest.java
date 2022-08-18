package com.woowacourse.moamoa.study.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.WebMVCTest;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class BadRequestMyMemberRoleWebMvcTest extends WebMVCTest {

    @DisplayName("study Id가 없을 경우 400 에러가 발생한다. ")
    @Test
    void getMyStudiesWithoutStudyId() throws Exception {
        final String token = tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(get("/api/members/me/role")
                        .cookie(new Cookie(ACCESS_TOKEN, token))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("study Id가 String일 경우 400 에러가 발생한다.")
    @Test
    void getMyStudiesWithoutStudyId1() throws Exception {
        final String token = tokenProvider.createToken(1L).getAccessToken();

        mockMvc.perform(get("/api/members/me/role")
                        .param("study-id", "one")
                        .cookie(new Cookie(ACCESS_TOKEN, token))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
