package com.woowacourse.moamoa.docs;

import static com.woowacourse.fixtures.AuthFixtures.JWT_토큰;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import com.woowacourse.moamoa.study.domain.MyRole;
import com.woowacourse.moamoa.study.service.response.MyRoleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class MyStudyDocumentationTest extends DocumentationTest {

    @DisplayName("스터디에서 사용자의 역할을 조회한다.")
    @Test
    void getMyRoleInStudy() throws Exception {
        given(myStudyService.findMyRoleInStudy(any(), any())).willReturn(new MyRoleResponse(MyRole.MEMBER));

        mockMvc.perform(get("/api/members/me/role")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_토큰)
                        .queryParam("study-id", "3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("members/me/role",
                        requestHeaders(
                                headerWithName("Authorization").description(
                                        "Bearer Token")),
                        requestParameters(parameterWithName("study-id").description("스터디 ID")),
                        responseFields(fieldWithPath("role").type(JsonFieldType.STRING).description("해당 스터디에서 사용자의 역할"))))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
