package com.woowacourse.acceptance.test.auth;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import io.restassured.RestAssured;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Authorization code를 받아서 token을 발급한다.")
    @Test
    void getJwtToken() throws JsonProcessingException {
        final String authorizationCode = "AuthorizationCode";
        final String accessToken = "access-token";
        mockingGithubServerForGetAccessToken(authorizationCode, Map.of("access_token", accessToken,
                "token_type", "bearer",
                "scope", ""));
        mockingGithubServerForGetProfile(accessToken, HttpStatus.OK,
                new GithubProfileResponse(1L, "sc0116", "sc0116@moamoa.space",
                        "https://image", "github.com"));

        RestAssured.given(spec).log().all()
                .filter(document("auth/login",
                        requestParameters(parameterWithName("code").description("Authorization code")),
                        responseFields(
                                fieldWithPath("accessToken").type(STRING).description("사용자 토큰"),
                                fieldWithPath("expiredTime").type(NUMBER).description("유효시간")
                        )))
                .queryParam("code", authorizationCode)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", notNullValue())
                .body("expiredTime", notNullValue());
    }

    @Test
    @DisplayName("유효하지 않은 Authorization Code인 경우 401을 반환한다.")
    void get401ByInvalidAuthorizationCode() throws JsonProcessingException {
        final Map<String, String> response = Map.of("error", "has error",
                "error_description", "error description",
                "error_uri", "error uri");
        final String invalidAuthorizationCode = "Invalid Authorization Code";
        mockingGithubServerForGetAccessToken(invalidAuthorizationCode, response);

        RestAssured.given().log().all()
                .param("code", invalidAuthorizationCode)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("유효하지 않는 Access Token인 경우 401을 반환한다.")
    void get401ByInvalidAccessToken() throws JsonProcessingException {
        final String authorizationCode = "Authorization Code";
        final String accessToken = "access_token";
        final Map<String, String> accessTokenResponse = Map.of(accessToken, "access-token",
                "token_type", "bearer",
                "scope", "");

        mockingGithubServerForGetAccessToken(authorizationCode, accessTokenResponse);
        mockingGithubServerForGetProfile("access-token", HttpStatus.UNAUTHORIZED);

        RestAssured.given().log().all()
                .param("code", authorizationCode)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private void mockingGithubServerForGetProfile(final String accessToken, final HttpStatus status)
            throws JsonProcessingException {
        mockingGithubServerForGetProfile(accessToken, status, null);
    }
}
