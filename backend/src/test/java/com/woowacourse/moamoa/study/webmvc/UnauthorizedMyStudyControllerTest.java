package com.woowacourse.moamoa.study.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.study.controller.MyStudyController;
import com.woowacourse.moamoa.study.service.MyStudyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MyStudyController.class)
@Import(JwtTokenProvider.class)
class UnauthorizedMyStudyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyStudyService myStudyService;

    @DisplayName("헤더에 Authorization 코드가 없이, 내 스터디를 조회할 경우 401 에러가 발생한다.")
    @Test
    void getMyStudiesWithoutAuthorization() throws Exception {
        mockMvc.perform(get("/api/my/studies"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
