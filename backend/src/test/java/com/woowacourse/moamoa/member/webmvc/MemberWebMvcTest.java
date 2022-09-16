package com.woowacourse.moamoa.member.webmvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.member.service.MemberService;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;

class MemberWebMvcTest extends WebMVCTest {

    @MockBean
    MemberService memberService;

    @DisplayName("잘못된 토큰 사용시 401 에러 반환")
    @ParameterizedTest
    @ValueSource(strings = {"bearer invalid.token", "invalid.token"})
    void unauthorizedToken(String invalidToken) throws Exception {
        mockMvc.perform(get("/api/members/me").cookie(new Cookie(ACCESS_TOKEN, invalidToken)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("사용자가 없는 경우 400 에러 반환")
    @Test
    void notFound() throws Exception {
        final String token = tokenProvider.createToken(1L).getAccessToken();
        given(memberService.getByMemberId(any())).willThrow(MemberNotFoundException.class);

        mockMvc.perform(get("/api/members/me").cookie(new Cookie(ACCESS_TOKEN, token)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
