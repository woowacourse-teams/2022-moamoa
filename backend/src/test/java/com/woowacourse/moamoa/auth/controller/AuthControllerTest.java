package com.woowacourse.moamoa.auth.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.auth.controller.matcher.AuthenticationRequestMatcher;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.auth.service.response.TokenResponseWithRefresh;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {AuthController.class})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private AuthenticationRequestMatcher authenticationRequestMatcher;

    @DisplayName("Authorization 요청과 응답 형식을 확인한다.")
    @Test
    void getJwtToken() throws Exception {
        given(authService.createToken("Authorization code")).willReturn(new TokenResponseWithRefresh("jwt token", "refresh token"));

        mockMvc.perform(post("/api/login/token?code=Authorization code"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken")
                        .value("jwt token"))
                .andDo(print());
    }
}
