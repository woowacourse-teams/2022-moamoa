package com.woowacourse.acceptance.test.study;

import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.member.query.data.ParticipatingMemberData;
import com.woowacourse.moamoa.study.service.response.StudyDetailResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

class StudyParticipantAcceptanceTest extends AcceptanceTest {

    @DisplayName("스터디에 참여한다.")
    @Test
    void participateStudy() {
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).모집인원은(10).생성한다();

        디우가().로그인하고().스터디에(자바_스터디_ID).참여에_성공하고_방장에게_알림을_보낸다("green");
    }

    @DisplayName("스터디를 탈퇴한다.")
    @Test
    void leaveStudy() {
        final LocalDate 지금 = LocalDate.now();
        final Long studyId = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();

        디우가().로그인하고().스터디에(studyId).참여에_성공한다();
        final String token = 디우가().로그인한다();

        RestAssured.given(spec).log().all()
                .filter(document("studies/leave",
                        requestHeaders(
                                headerWithName("Authorization").description("JWT Token")
                        )))
                .header(HttpHeaders.AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .when().log().all()
                .delete("/api/studies/{study-id}/members")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        final StudyDetailResponse studyDetailResponse = RestAssured.given().log().all()
                .pathParam("study-id", studyId)
                .when().log().all()
                .get("/api/studies/{study-id}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(StudyDetailResponse.class);

        assertAll(
                () -> assertThat(studyDetailResponse.getCurrentMemberCount()).isEqualTo(1),
                () -> assertThat(studyDetailResponse.getMembers()).isEmpty()
        );
    }

    @DisplayName("방장은 스터디원을 강퇴시킬 수 있다.")
    @Test
    void kickOutStudyMember() {
        final LocalDate 지금 = LocalDate.now();
        final Long 스터디_아이디 = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();

        디우가().로그인하고().스터디에(스터디_아이디).참여에_성공한다();
        final Long 디우_아이디 = 디우가().로그인하고().정보를_가져온다().getId();

        final String 짱구_토큰 = 짱구가().로그인한다();

        RestAssured.given(spec).log().all()
                .filter(document("studies/kick-out",
                        requestHeaders(
                                headerWithName("Authorization").description("JWT Token")
                        )))
                .header(HttpHeaders.AUTHORIZATION, 짱구_토큰)
                .pathParam("study-id", 스터디_아이디)
                .pathParam("member-id", 디우_아이디)
                .when().log().all()
                .delete("/api/studies/{study-id}/members/{member-id}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        final StudyDetailResponse 스터디_상세정보 = 짱구가().로그인하고().스터디에(스터디_아이디).정보를_가져온다();
        final List<Long> 스터디원_아이디_목록 = 스터디_상세정보.getMembers()
                .stream()
                .map(ParticipatingMemberData::getId)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(스터디_상세정보.getCurrentMemberCount()).isOne(),
                () -> assertThat(스터디원_아이디_목록).isNotNull(),
                () -> assertThat(스터디원_아이디_목록).doesNotContain(디우_아이디)
        );
    }
}
