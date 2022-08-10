package com.woowacourse.moamoa.auth.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthServiceTest extends WebMVCTest {

    @DisplayName("Authorization code를 받아서 token을 발급한다.")
    @Test
    void getTokenByAuthorizationCode() throws Exception {
        when(authService.createToken("authorization-code")).thenReturn(new TokenResponse("this is jwt-token"));

        mockMvc.perform(post("/api/login/token")
                        .param("code", "authorization-code"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").value("this is jwt-token"))
                .andDo(print());
    }
}
