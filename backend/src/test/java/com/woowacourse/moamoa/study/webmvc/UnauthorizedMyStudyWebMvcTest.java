package com.woowacourse.moamoa.study.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.woowacourse.moamoa.common.WebMVCTest;

class UnauthorizedMyStudyWebMvcTest extends WebMVCTest {

    @DisplayName("헤더에 Authorization 코드가 없이, 내 스터디를 조회할 경우 401 에러가 발생한다.")
    @ParameterizedTest
    @CsvSource({"/api/my/studies", "/api/members/me/role"})
    void getMyStudiesWithoutAuthorization(String path) throws Exception {
        mockMvc.perform(get(path))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
