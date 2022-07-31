package com.woowacourse.moamoa.auth.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.auth.controller.AuthController;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {AuthController.class})
@MockBean(JpaMetamodelMappingContext.class)
class AuthServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenProvider tokenProvider;

    @DisplayName("Authorization code를 받아서 token을 발급한다.")
    @Test
    void getTokenByAuthorizationCode() throws Exception {
        when(authService.createToken("authorization-code")).thenReturn(new TokenResponse("this is jwt-token"));

        mockMvc.perform(post("/api/login/token")
                        .param("code", "authorization-code"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").value("this is jwt-token"))
                .andDo(print());
    }
}
