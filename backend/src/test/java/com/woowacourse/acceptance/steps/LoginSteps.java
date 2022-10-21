package com.woowacourse.acceptance.steps;

import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_깃허브_프로필;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_깃허브_프로필;
import static com.woowacourse.acceptance.fixture.MemberFixtures.베루스_깃허브_프로필;
import static com.woowacourse.acceptance.fixture.MemberFixtures.짱구_깃허브_프로필;

import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class LoginSteps extends Steps {

    private final GithubProfileResponse githubProfile;

    LoginSteps(final GithubProfileResponse githubProfile) {
        this.githubProfile = githubProfile;
    }

    public static LoginSteps 짱구가() {
        return 사용자가(짱구_깃허브_프로필);
    }

    public static LoginSteps 그린론이() {
        return 사용자가(그린론_깃허브_프로필);
    }

    public static LoginSteps 디우가() {
        return 사용자가(디우_깃허브_프로필);
    }

    public static LoginSteps 베루스가() {
        return 사용자가(베루스_깃허브_프로필);
    }

    public static LoginSteps 사용자가(final GithubProfileResponse response) {
        return new LoginSteps(response);
    }

    public AfterLoginSteps 로그인하고() {
        return new AfterLoginSteps(로그인한다());
    }

    public String 로그인한다() {
        if (tokenCache.containsKey(githubProfile.getGithubId())) {
            return tokenCache.get(githubProfile.getGithubId());
        }

        final String bearerToken = requestBearerToken();
        tokenCache.put(githubProfile.getGithubId(), bearerToken);

        return bearerToken;
    }

    private String requestBearerToken() {
        final String authorizationCode = "Authorization Code" + githubProfile.getGithubId();

        mockServer.reset();
        mockingGithubServer(authorizationCode, githubProfile);

        final String token = RestAssured.given().log().all()
                .param("code", authorizationCode)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().getString("accessToken");

        mockServer.reset();

        return "Bearer " + token;
    }
}
