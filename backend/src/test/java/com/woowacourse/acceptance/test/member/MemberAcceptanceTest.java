package com.woowacourse.acceptance.test.member;

import static com.woowacourse.acceptance.fixture.MemberFixture.베루스_깃허브_ID;
import static com.woowacourse.acceptance.fixture.MemberFixture.베루스_이름;
import static com.woowacourse.acceptance.fixture.MemberFixture.베루스_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixture.베루스_프로필_URL;
import static com.woowacourse.acceptance.steps.LoginSteps.베루스가;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import io.restassured.RestAssured;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void getCurrentMember() {
        final String token = 베루스가().로그인한다();

        final MemberResponse memberResponse = RestAssured.given(spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .filter(document("members/me",
                        requestHeaders(headerWithName("Authorization").description("Bearer Token"))))
                .when().log().all()
                .get("/api/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberResponse.class);

        assertThat(memberResponse.getId()).isEqualTo(베루스_깃허브_ID);
        assertThat(memberResponse.getUsername()).isEqualTo(베루스_이름);
        assertThat(memberResponse.getImageUrl()).isEqualTo(베루스_이미지_URL);
        assertThat(memberResponse.getProfileUrl()).isEqualTo(베루스_프로필_URL);
    }
}
