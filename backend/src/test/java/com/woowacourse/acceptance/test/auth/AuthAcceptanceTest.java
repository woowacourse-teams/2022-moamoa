package com.woowacourse.acceptance.test.auth;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.domain.Token;
import com.woowacourse.moamoa.auth.domain.repository.TokenRepository;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import io.restassured.RestAssured;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class AuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    private TokenRepository tokenRepository;

    @DisplayName("Authorization code를 받아서 token을 발급한다.")
    @Test
    void getJwtToken() throws JsonProcessingException {
        final String authorizationCode = "AuthorizationCode";
        final String accessToken = "access-token";
        mockingGithubServerForGetAccessToken(authorizationCode, Map.of("access_token", accessToken,
                "token_type", "bearer",
                "scope", ""));
        mockingGithubServerForGetProfile(accessToken, HttpStatus.OK,
                new GithubProfileResponse(1L, "sc0116", "https://image", "github.com"));

        RestAssured.given(spec).log().all()
                .filter(document("auth/login",
                        requestParameters(parameterWithName("code").description("Authorization code")),
                        responseFields(
                                fieldWithPath("accessToken").type(STRING).description("사용자 토큰")
                        )))
                .queryParam("code", authorizationCode)
                .when()
                .post("/api/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", notNullValue());
    }

    @DisplayName("RefreshToken 으로 AccessToken 을 재발급한다.")
    @Test
    void refreshToken() {
        final String token = getBearerTokenBySignInOrUp(new GithubProfileResponse(4L, "verus", "https://image", "github.com"));
        final Token foundToken = tokenRepository.findByGithubId(4L).get();

        RestAssured.given(spec).log().all()
                .filter(document("auth/refresh",
                        requestHeaders(headerWithName("Authorization").description("Bearer Token"))))
                .cookie("refreshToken", foundToken.getRefreshToken())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .when()
                .get("/api/auth/refresh")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", notNullValue());
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
                .post("/api/login/token")
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
                .post("/api/login/token")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private void mockingGithubServerForGetProfile(final String accessToken, final HttpStatus status)
            throws JsonProcessingException {
        mockingGithubServerForGetProfile(accessToken, status, null);
    }
}
