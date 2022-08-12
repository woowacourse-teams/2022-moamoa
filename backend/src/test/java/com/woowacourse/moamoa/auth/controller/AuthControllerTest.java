package com.woowacourse.moamoa.auth.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.auth.service.response.TokensResponse;
import com.woowacourse.moamoa.auth.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class AuthControllerTest extends WebMVCTest {

    @MockBean
    AuthService authService;

    @DisplayName("Authorization 요청과 응답 형식을 확인한다.")
    @Test
    void getJwtToken() throws Exception {
        given(authService.createToken("Authorization code"))
                .willReturn(new TokensResponse("jwt token", "refreshtoken"));

        mockMvc.perform(post("/api/auth/login?code=Authorization code"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("jwt token"))
                .andDo(print());
    }
}
