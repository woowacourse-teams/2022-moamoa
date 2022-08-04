package com.woowacourse.acceptance.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import io.restassured.RestAssured;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MemberAcceptanceTest extends AcceptanceTest {

    private String token;

    @BeforeEach
    void setUp() {
        token = getBearerTokenBySignInOrUp(new GithubProfileResponse(1L, "verus", "image", "profile"));
    }

    @Test
    void getCurrentMember() {
        final MemberResponse memberResponse = RestAssured.given(spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .filter(document("members/me"))
                .when().log().all()
                .get("/api/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberResponse.class);

        assertThat(memberResponse.getId()).isEqualTo(1L);
        assertThat(memberResponse.getUsername()).isEqualTo("verus");
        assertThat(memberResponse.getImageUrl()).isEqualTo("image");
        assertThat(memberResponse.getProfileUrl()).isEqualTo("profile");
    }
}
