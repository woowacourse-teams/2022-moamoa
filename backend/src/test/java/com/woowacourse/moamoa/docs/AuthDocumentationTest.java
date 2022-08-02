package com.woowacourse.moamoa.docs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class AuthDocumentationTest extends DocumentationTest {

    @DisplayName("Github 로그인에 성공하면 200을 응답한다.")
    @Test
    void login() throws Exception {
        given(authService.createToken(any())).willReturn(new TokenResponse("JWT 토큰"));

        mockMvc.perform(post("/api/login/token")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("code", "authorization-code"))
                .andDo(document("auth/login"))
                .andExpect(status().isOk());
    }
}
