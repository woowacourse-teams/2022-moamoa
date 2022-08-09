package com.woowacourse.moamoa.member.webmvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.auth.config.AuthRequestMatchConfig;
import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.member.controller.MemberController;
import com.woowacourse.moamoa.member.service.MemberService;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {MemberController.class})
@Import({JwtTokenProvider.class, AuthRequestMatchConfig.class})
public class MemberWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Autowired
    TokenProvider tokenProvider;

    @DisplayName("잘못된 토큰 사용시 401 에러 반환")
    @ParameterizedTest
    @ValueSource(strings = {"bearer invalid.token", "invalid.token"})
    void unauthorizedToken(String invalidToken) throws Exception {
        mockMvc.perform(get("/api/members/me")
                .header(HttpHeaders.AUTHORIZATION, invalidToken)
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("사용자가 없는 경우 400 에러 반환")
    @Test
    void notFound() throws Exception {
        final String token = tokenProvider.createToken(1L).getAccessToken();
        given(memberService.getByGithubId(any())).willThrow(MemberNotFoundException.class);

        mockMvc.perform(get("/api/members/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
