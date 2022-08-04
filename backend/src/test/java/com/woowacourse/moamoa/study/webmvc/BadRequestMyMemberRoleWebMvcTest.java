package com.woowacourse.moamoa.study.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.woowacourse.moamoa.auth.controller.matcher.AuthenticationRequestMatcher;
import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.study.controller.MyStudyController;
import com.woowacourse.moamoa.study.service.MyStudyService;

@WebMvcTest(controllers = MyStudyController.class)
@Import(JwtTokenProvider.class)
public class BadRequestMyMemberRoleWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyStudyService myStudyService;

    @MockBean
    private AuthenticationRequestMatcher authenticationRequestMatcher;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("study Id가 없을 경우 400 에러가 발생한다. ")
    @Test
    void getMyStudiesWithoutAuthorization() throws Exception {
        final String token = "Bearer " + jwtTokenProvider.createToken(1L);

        mockMvc.perform(get("/api/members/me/role")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
