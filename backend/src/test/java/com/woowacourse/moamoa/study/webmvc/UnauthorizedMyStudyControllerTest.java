package com.woowacourse.moamoa.study.webmvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.controller.MyStudyController;
import com.woowacourse.moamoa.study.query.data.MyStudyData;
import com.woowacourse.moamoa.study.service.MyStudyService;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.List;
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
        given(myStudyService.getStudies(1L)).willReturn(new MyStudiesResponse(
                List.of(
                        new MyStudyData(1L, "Java 스터디", "IN_PROGRESS", 3, 10,
                                "2022-07-26", "2022-08-26",
                                new MemberData(2L, "jaejae-yoo", "images/123", "https://github.com/user/jaejae-yoo"),
                                List.of(new TagSummaryData(1L, "BE"), new TagSummaryData(5L, "Java"))
                        )
                )
        ));

        mockMvc.perform(get("/api/my/studies"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}