package com.woowacourse.moamoa.study.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.data.MyStudyData;
import com.woowacourse.moamoa.study.service.MyStudyService;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

@WebMvcTest(controllers = MyStudyController.class)
@Import(JwtTokenProvider.class)
class MyStudyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyStudyService myStudyService;

    @Autowired
    private TokenProvider tokenProvider;


    @DisplayName("내가 참여한 스터디를 조회한다.")
    @Test
    void getMyStudies() throws Exception {
        String token = "Bearer " + tokenProvider.createToken(2L);

        given(myStudyService.getStudies(1L)).willReturn(new MyStudiesResponse(
                List.of(
                        new MyStudyData(1L, "Java 스터디", "IN_PROGRESS", 3, 10,
                                "2022-07-26", "2022-08-26",
                                new MemberData(2L, "jaejae-yoo", "images/123", "https://github.com/user/jaejae-yoo"),
                                List.of(new TagSummaryData(1L, "BE"), new TagSummaryData(5L, "Java"))
                        )
                )
        ));

        mockMvc.perform(get("/api/my/studies")
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("studies[0].id").value(1L))
                .andExpect(jsonPath("studies[0].title").value("Java 스터디"))
                .andExpect(jsonPath("studies[0].studyStatus").value("IN_PROGRESS"))
                .andExpect(jsonPath("studies[0].startDate").value("2022-07-26"))
                .andExpect(jsonPath("studies[0].endDate").value("2022-08-26"))
                .andExpect(jsonPath("studies[0].owner.id").value(2L))
                .andExpect(jsonPath("studies[0].owner.username").value("jaejae-yoo"))
                .andExpect(jsonPath("studies[0].owner.imageUrl").value("images/123"))
                .andExpect(jsonPath("studies[0].owner.profileUrl").value("https://github.com/user/jaejae-yoo"))
                .andExpect(jsonPath("studies[0].tags.[0].id").value(1L))
                .andExpect(jsonPath("studies[0].tags.[0].name").value("BE"))
                .andExpect(jsonPath("studies[0].tags.[1].id").value(5L))
                .andExpect(jsonPath("studies[0].tags.[1].name").value("Java"))
                .andDo(print());
    }

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