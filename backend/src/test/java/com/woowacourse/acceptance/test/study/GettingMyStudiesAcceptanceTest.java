package com.woowacourse.acceptance.test.study;

import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_프로필_URL;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리액트_스터디_제목;
import static com.woowacourse.acceptance.fixture.StudyFixtures.자바_스터디_제목;
import static com.woowacourse.acceptance.fixture.TagFixtures.BE_태그_ID;
import static com.woowacourse.acceptance.fixture.TagFixtures.BE_태그명;
import static com.woowacourse.acceptance.fixture.TagFixtures.자바_태그_ID;
import static com.woowacourse.acceptance.fixture.TagFixtures.자바_태그명;
import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.slack.api.model.Attachment;
import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.alarm.request.SlackMessageRequest;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.study.domain.StudyStatus;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import com.woowacourse.moamoa.study.service.response.MyStudyResponse;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

class GettingMyStudiesAcceptanceTest extends AcceptanceTest {

    @DisplayName("내가 참여한 스터디를 조회한다.")
    @Test
    void getMyStudies() {
        // arrange
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).태그는(자바_태그_ID, BE_태그_ID).생성한다();
        long 리액트_스터디_ID = 디우가().로그인하고().리액트_스터디를().시작일자는(지금.plusDays(10)).생성한다();

        final SlackMessageRequest slackMessageRequest = new SlackMessageRequest("dwoo",
                List.of(Attachment.builder().title("📚 스터디에 새로운 크루가 참여했습니다.")
                        .text("<https://moamoa.space/my/study/|모아모아 바로가기>")
                        .color("#36288f").build()));

        그린론이().로그인하고().스터디에(리액트_스터디_ID).참여한다(slackAlarmMockServer, slackMessageRequest);
        final String token = 그린론이().로그인한다();

        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();
        final MemberResponse 디우_정보 = 디우가().로그인하고().정보를_가져온다();

        // act
        final MyStudiesResponse body = RestAssured.given(spec).log().all()
                .filter(document("studies/myStudy"))
                .header(AUTHORIZATION, token)
                .when().log().all()
                .get("/api/my/studies")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(MyStudiesResponse.class);

        // assert
        MyStudyResponse expectedJava = new MyStudyResponse(
                new MyStudySummaryData(자바_스터디_ID, 자바_스터디_제목, IN_PROGRESS, 1,
                        null, 지금.toString(), null),
                new MemberData(그린론_정보.getId(), 그린론_이름, 그린론_이미지_URL, 그린론_프로필_URL),
                List.of(new TagSummaryData(자바_태그_ID, 자바_태그명), new TagSummaryData(BE_태그_ID, BE_태그명)));

        MyStudyResponse expectedReact = new MyStudyResponse(
                new MyStudySummaryData(리액트_스터디_ID, 리액트_스터디_제목, StudyStatus.PREPARE, 2,
                        null, 지금.plusDays(10).toString(), null),
                new MemberData(디우_정보.getId(), 디우_이름, 디우_이미지_URL, 디우_프로필_URL),
                List.of());

        assertThat(body.getStudies())
                .hasSize(2)
                .containsExactlyInAnyOrder(expectedJava, expectedReact);
    }

    @Test
    @DisplayName("특정 스터디에서 나의 Role을 확인한다.")
    void isMyStudy() {
        // arrange
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        String token = 그린론이().로그인한다();

        // act & assert
        RestAssured.given(spec).log().all()
                .filter(document("members/me/role",
                        requestHeaders(headerWithName("Authorization").description("Bearer Token")),
                        requestParameters(parameterWithName("study-id").description("스터디 ID")),
                        responseFields(
                                fieldWithPath("role").type(JsonFieldType.STRING).description("해당 스터디에서 사용자의 역할"))))
                .filter(document("members/me/role"))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .queryParam("study-id", 자바_스터디_ID)
                .when()
                .get("/api/members/me/role")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("role", is("OWNER"));
    }
}
