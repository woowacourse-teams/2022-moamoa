package com.woowacourse.docs;

import static com.woowacourse.fixtures.AuthFixtures.JWT_토큰;
import static com.woowacourse.fixtures.StudyFixtures.리액트_스터디_응답;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디_응답;
import static com.woowacourse.fixtures.TagFixtures.BE_태그_아이디;
import static com.woowacourse.fixtures.TagFixtures.우테코4기_태그_아이디;
import static com.woowacourse.fixtures.TagFixtures.자바_태그_아이디;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class StudyDocumentationTest extends DocumentationTest {

    @DisplayName("스터디를 생성한다.")
    @Test
    void create() throws Exception {
        final CreatingStudyRequest request = new CreatingStudyRequest("신짱구의 자바의 정석", "자바 스터디 요약",
                "자바 스터디 썸네일", "자바 스터디 설명입니다.", 10,
                LocalDate.now().plusMonths(1), LocalDate.now(), LocalDate.now().plusMonths(2),
                List.of(자바_태그_아이디, 우테코4기_태그_아이디, BE_태그_아이디));
        given(studyService.createStudy(any(), any())).willReturn(자바_스터디);

        mockMvc.perform(post("/api/studies")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_토큰)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(document("studies/create"))
                .andExpect(status().isCreated());
    }

    @DisplayName("스터디 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        given(searchingStudyService.getStudies(any(), any(), any()))
                .willReturn(new StudiesResponse(List.of(자바_스터디_응답, 리액트_스터디_응답), true));

        mockMvc.perform(get("/api/studies?page=0&size=2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("studies/list"))
                .andExpect(status().isOk());
    }
}
