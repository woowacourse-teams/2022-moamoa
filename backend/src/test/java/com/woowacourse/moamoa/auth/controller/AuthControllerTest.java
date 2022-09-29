package com.woowacourse.moamoa.auth.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.response.TokensResponse;
import com.woowacourse.moamoa.auth.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

class AuthControllerTest extends WebMVCTest {

    @MockBean
    AuthService authService;

    @MockBean
    OAuthClient oAuthClient;

    @DisplayName("Authorization 요청과 응답 형식을 확인한다.")
    @Test
    void getJwtToken() throws Exception {
        final GithubProfileResponse dwoo = new GithubProfileResponse(1L, "dwoo", "http://imageUrl",
                "http://profileUrl");

        given(oAuthClient.getAccessToken("Authorization code"))
                .willReturn("AccessToken");
        given(oAuthClient.getProfile("AccessToken"))
                .willReturn(dwoo);
        given(authService.createToken(dwoo))
                .willReturn(new TokensResponse("jwt token", "refreshtoken"));

        mockMvc.perform(post("/api/auth/login?code=Authorization code"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("jwt token"))
                .andDo(print());
    }
}
