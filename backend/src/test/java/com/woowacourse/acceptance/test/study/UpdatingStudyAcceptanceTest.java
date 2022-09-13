package com.woowacourse.acceptance.test.study;

import static com.woowacourse.acceptance.fixture.TagFixtures.BE_태그_ID;
import static com.woowacourse.acceptance.fixture.TagFixtures.우테코4기_태그_ID;
import static com.woowacourse.acceptance.fixture.TagFixtures.자바_태그_ID;
import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import com.woowacourse.moamoa.study.service.request.StudyRequestBuilder;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class UpdatingStudyAcceptanceTest extends AcceptanceTest {

    @DisplayName("스터디 내용을 수정할 수 있다.")
    @Test
    void updateStudy() {
        final LocalDate 지금 = LocalDate.now();
        final long studyId = 짱구가().로그인하고().자바_스터디를()
                .시작일자는(지금).태그는(자바_태그_ID, 우테코4기_태그_ID, BE_태그_ID)
                .생성한다();
        final String accessToken = 짱구가().로그인한다();

        final StudyRequest request = new StudyRequestBuilder().title("변경된 제목")
                .description("변경된 설명")
                .excerpt("변경된 한 줄 설명")
                .thumbnail("변경된 썸네일")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .enrollmentEndDate(LocalDate.now().plusDays(5))
                .tagIds(List.of(자바_태그_ID, 우테코4기_태그_ID))
                .build();

        RestAssured.given(spec).log().all()
                .filter(document("studies/update"))
                .cookie(ACCESS_TOKEN, accessToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", studyId)
                .body(request)
                .when().log().all()
                .put("/api/studies/{study-id}")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("이전 날짜로 스터디 모집 기간을 변경할 수 없다.")
    @Test
    void updateStudyWithBeforeDay() {
        final LocalDate 지금 = LocalDate.now();
        final long studyId = 짱구가().로그인하고().자바_스터디를()
                .시작일자는(지금).태그는(자바_태그_ID, 우테코4기_태그_ID, BE_태그_ID)
                .생성한다();
        final String accessToken = 짱구가().로그인한다();

        final StudyRequest request = new StudyRequestBuilder().title("변경된 제목")
                .description("변경된 설명")
                .excerpt("변경된 한 줄 설명")
                .thumbnail("변경된 썸네일")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .enrollmentEndDate(LocalDate.now().minusDays(1))
                .tagIds(List.of(자바_태그_ID, 우테코4기_태그_ID))
                .build();

        RestAssured.given(spec).log().all()
                .filter(document("studies/update",
                        requestHeaders(headerWithName("Authorization").description("Bearer Token"))))
                .header(AUTHORIZATION, accessToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", studyId)
                .body(request)
                .when().log().all()
                .put("/api/studies/{study-id}")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("스터디 모집 인원을 현재 인원보다 적게 변경할 수 없다.")
    @Test
    void updateStudyWithLessThanCurrentMember() {
        final LocalDate 지금 = LocalDate.now();
        final long studyId = 짱구가().로그인하고().자바_스터디를()
                .시작일자는(지금).태그는(자바_태그_ID, 우테코4기_태그_ID, BE_태그_ID)
                .생성한다();
        디우가().로그인하고().스터디에(studyId).참여한다();
        final String accessToken = 짱구가().로그인한다();

        final StudyRequest request = new StudyRequestBuilder().title("변경된 제목")
                .description("변경된 설명")
                .excerpt("변경된 한 줄 설명")
                .thumbnail("변경된 썸네일")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .enrollmentEndDate(LocalDate.now().plusDays(5))
                .maxMemberCount(1)
                .tagIds(List.of(자바_태그_ID, 우테코4기_태그_ID))
                .build();

        RestAssured.given(spec).log().all()
                .filter(document("studies/update",
                        requestHeaders(headerWithName("Authorization").description("Bearer Token"))))
                .header(AUTHORIZATION, accessToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", studyId)
                .body(request)
                .when().log().all()
                .put("/api/studies/{study-id}")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
