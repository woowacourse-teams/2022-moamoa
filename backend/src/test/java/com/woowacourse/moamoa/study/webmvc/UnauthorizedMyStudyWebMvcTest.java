package com.woowacourse.moamoa.study.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.woowacourse.moamoa.auth.controller.matcher.AuthenticationRequestMatcher;
import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.study.controller.MyStudyController;
import com.woowacourse.moamoa.study.service.MyStudyService;

@WebMvcTest(controllers = MyStudyController.class)
@Import(JwtTokenProvider.class)
class UnauthorizedMyStudyWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyStudyService myStudyService;

    @MockBean
    private AuthenticationRequestMatcher authenticationRequestMatcher;

    @DisplayName("헤더에 Authorization 코드가 없이, 내 스터디를 조회할 경우 401 에러가 발생한다.")
    @ParameterizedTest
    @CsvSource({"/api/my/studies", "/api/members/me/role"})
    void getMyStudiesWithoutAuthorization(String path) throws Exception {
        mockMvc.perform(get(path))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
