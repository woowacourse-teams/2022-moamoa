package com.woowacourse.moamoa.community.webmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.WebMVCTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CommunityControllerWebMvcTest extends WebMVCTest {

    @DisplayName("잘못된 토큰으로 커뮤니티 글을 생성할 경우 401을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "Bearer InvalidToken", "Invalid"})
    void UnauthorizedByInvalidToken(String token) throws Exception {
        mockMvc.perform(post("/api/studies/{study-id}/notice/articles", 1L))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
