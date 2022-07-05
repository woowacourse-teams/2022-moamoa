package com.woowacourse.moamoa.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.moamoa.controller.dto.StudiesResponse;
import com.woowacourse.moamoa.domain.Study;
import com.woowacourse.moamoa.repository.StudyRepository;
import com.woowacourse.moamoa.service.StudyService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = StudyController.class)
@Import(StudyService.class)
public class StudyControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private StudyRepository studyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @BeforeEach
    void setUp() {
        SliceImpl<Study> slice = new SliceImpl<>(
                List.of(
                        new Study(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        new Study(2L, "React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        new Study(3L, "javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"),
                        new Study(4L, "HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        new Study(5L, "알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE")
                )
        );

        when(studyRepository.findAllBy(any())).thenReturn(slice);
    }

    @DisplayName("잘못된 페이징 정보로 스터디 목록 조회 시 400 반환")
    @ParameterizedTest
    @CsvSource({"-1,3", "1,0", "one,1", "1,one"})
    void return400ByInvalidPagingInfo(String page, String size) throws Exception {
        mockMvc.perform(get("/api/studies")
                        .param("page", page)
                        .param("size", size)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", not(blankOrNullString())));
    }

    @DisplayName("기본값 페이징 정보로 스터디 목록 조회")
    @Test
    public void getStudiesByDefaultPagingInfo() throws Exception {
        final String content = mockMvc.perform(get("/api/studies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        final StudiesResponse studiesResponse = objectMapper.readValue(content, StudiesResponse.class);

        assertThat(studiesResponse.getStudies())
                .hasSize(5)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "description", "thumbnail", "status")
                .containsExactlyInAnyOrder(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE")
                );
    }

    @DisplayName("페이징 정보로 스터디 목록 조회")
    @Test
    public void getStudiesByPagingInfo() throws Exception {
        final String content = mockMvc.perform(get("/api/studies?page=0&size=5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        final StudiesResponse studiesResponse = objectMapper.readValue(content, StudiesResponse.class);

        assertThat(studiesResponse.getStudies())
                .hasSize(5)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "description", "thumbnail", "status")
                .containsExactlyInAnyOrder(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE")
                );
    }

    @DisplayName("페이지가 입력되지 않은 경우 목록 조회")
    @Test
    public void getStudiesByDefaultPage() throws Exception {
        mockMvc.perform(get("/api/studies?size=5"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", not(blankOrNullString())));;
    }

    @DisplayName("사이즈가 입력되지 않은 경우 목록 조회")
    @Test
    public void getStudiesByDefaultSize() throws Exception {
        mockMvc.perform(get("/api/studies?page=0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", not(blankOrNullString())));;
    }

}
