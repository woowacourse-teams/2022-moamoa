package com.woowacourse.acceptance.test.auth;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.domain.Token;
import com.woowacourse.moamoa.auth.domain.repository.TokenRepository;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import io.restassured.RestAssured;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class AuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MemberRepository memberRepository;

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
                        requestParameters(parameterWithName("code").description("Authorization code"))))
                .queryParam("code", authorizationCode)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .cookie("accessToken", notNullValue())
                .cookie("refreshToken", notNullValue());
    }

    @DisplayName("RefreshToken 으로 AccessToken 을 재발급한다.")
    @Test
    void refreshToken() {
        final String token = getBearerTokenBySignInOrUp(new GithubProfileResponse(4L, "verus", "https://image", "github.com"));
        final Member member = memberRepository.findByGithubId(4L).get();
        final Token foundToken = tokenRepository.findByMemberId(member.getId()).get();

        RestAssured.given(spec).log().all()
                .filter(document("auth/refresh"))
                .cookie("refreshToken", foundToken.getRefreshToken())
                .cookie(ACCESS_TOKEN, token)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .when()
                .get("/api/auth/refresh")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .cookie("accessToken", notNullValue());
    }

    @DisplayName("로그아웃시에 쿠키를 제거해준다.")
    @Test
    void logout() {
        final String token = getBearerTokenBySignInOrUp(new GithubProfileResponse(4L, "verus", "https://image", "github.com"));
        final Member member = memberRepository.findByGithubId(4L).get();
        final Token foundToken = tokenRepository.findByMemberId(member.getId()).get();

        RestAssured.given(spec).log().all()
                .filter(document("auth/logout"))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .cookie("refreshToken", foundToken.getRefreshToken())
                .cookie(ACCESS_TOKEN, token)
                .when()
                .delete("/api/auth/logout")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .cookie("refreshToken", is(""));
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

    private String getBearerTokenBySignInOrUp(GithubProfileResponse response) {
        final String authorizationCode = "Authorization Code";
        mockingGithubServer(authorizationCode, response);
        final String token = RestAssured.given().log().all()
                .param("code", authorizationCode)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().cookie("accessToken");

        mockServer.reset();
        return token;
    }

    private void mockingGithubServer(String authorizationCode, GithubProfileResponse response) {
        try {
            mockingGithubServerForGetAccessToken(authorizationCode, Map.of(
                    "access_token", "access-token",
                    "token_type", "bearer",
                    "scope", ""));
            mockingGithubServerForGetProfile("access-token", HttpStatus.OK, response);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
