package com.woowacourse.moamoa.controller;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = StudyController.class)
public class StudyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("잘못된 페이징 정보로 스터디 목록 조회 시 400 반환")
    @ParameterizedTest
    @CsvSource({"0,3", "1,0", "one,1", "1,one"})
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
        mockMvc.perform(get("/api/studies"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
