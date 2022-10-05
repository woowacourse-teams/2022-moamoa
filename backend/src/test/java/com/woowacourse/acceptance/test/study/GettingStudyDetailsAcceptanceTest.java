package com.woowacourse.acceptance.test.study;

import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.베루스_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.베루스_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.베루스_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.짱구_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.짱구_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.짱구_프로필_URL;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리액트_스터디_설명;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리액트_스터디_썸네일;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리액트_스터디_요약;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리액트_스터디_제목;
import static com.woowacourse.acceptance.fixture.StudyFixtures.알고리즘_스터디_설명;
import static com.woowacourse.acceptance.fixture.StudyFixtures.알고리즘_스터디_썸네일;
import static com.woowacourse.acceptance.fixture.StudyFixtures.알고리즘_스터디_요약;
import static com.woowacourse.acceptance.fixture.StudyFixtures.알고리즘_스터디_제목;
import static com.woowacourse.acceptance.fixture.TagFixtures.FE_태그_ID;
import static com.woowacourse.acceptance.fixture.TagFixtures.리액트_태그_ID;
import static com.woowacourse.acceptance.fixture.TagFixtures.우테코4기_태그_ID;
import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static com.woowacourse.acceptance.steps.LoginSteps.베루스가;
import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class GettingStudyDetailsAcceptanceTest extends AcceptanceTest {

    @DisplayName("스터디 요약 정보 외에 상세 정보를 포함하여 조회할 수 있다.")
    @Test
    void getStudyDetails() {
        LocalDate 지금 = LocalDate.now();
        long 리액트_스터디 = 디우가().로그인하고().리액트_스터디를()
                .시작일자는(지금).모집종료일자는(지금.plusDays(4)).종료일자는(지금.plusDays(10))
                .태그는(우테코4기_태그_ID, FE_태그_ID, 리액트_태그_ID).모집인원은(5)
                .생성한다();
        짱구가().로그인하고().스터디에(리액트_스터디).참여한다();
        그린론이().로그인하고().스터디에(리액트_스터디).참여한다();
        베루스가().로그인하고().스터디에(리액트_스터디).참여한다();

        RestAssured.given(spec).log().all()
                .filter(document("studies/details"))
                .pathParam("study-id", 리액트_스터디)
                .when().log().all()
                .get("/api/studies/{study-id}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(empty()))
                .body("title", is(리액트_스터디_제목))
                .body("excerpt", is(리액트_스터디_요약))
                .body("thumbnail", is(리액트_스터디_썸네일))
                .body("recruitmentStatus", is("RECRUITMENT_START"))
                .body("description", is(리액트_스터디_설명))
                .body("currentMemberCount", is(4))
                .body("maxMemberCount", is(5))
                .body("enrollmentEndDate", is(지금.plusDays(4).toString()))
                .body("startDate", is(지금.toString()))
                .body("endDate", is(지금.plusDays(10).toString()))
                .body("owner.id", not(empty()))
                .body("owner.username", is(디우_이름))
                .body("owner.imageUrl", is(디우_이미지_URL))
                .body("owner.profileUrl", is(디우_프로필_URL))
                .body("owner.participantDate", not(empty()))
                .body("owner.numberOfStudy", is(1))
                .body("members.id", not(empty()))
                .body("members.username", contains(짱구_이름, 그린론_이름, 베루스_이름))
                .body("members.imageUrl", contains(짱구_이미지_URL, 그린론_이미지_URL, 베루스_이미지_URL))
                .body("members.profileUrl", contains(짱구_프로필_URL, 그린론_프로필_URL, 베루스_프로필_URL))
                .body("members.participationDate", not(empty()))
                .body("members.numberOfStudy", contains(1, 1, 1))
                .body("tags.id", not(empty()))
                .body("tags.name", contains("4기", "FE", "React"));
    }

    @DisplayName("선택 데이터가 없는 스터디 세부사항을 조회한다.")
    @Test
    void getNotHasOptionalDataStudyDetails() {
        LocalDate 지금 = LocalDate.now();
        final long 알고리즘_스터디 = 베루스가().로그인하고().알고리즘_스터디를().시작일자는(지금).생성한다();

        RestAssured.given().log().all()
                .pathParam("study-id", 알고리즘_스터디)
                .when().log().all()
                .get("/api/studies/{study-id}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is((int)알고리즘_스터디))
                .body("title", is(알고리즘_스터디_제목))
                .body("excerpt", is(알고리즘_스터디_요약))
                .body("thumbnail", is(알고리즘_스터디_썸네일))
                .body("recruitmentStatus", is("RECRUITMENT_START"))
                .body("description", is(알고리즘_스터디_설명))
                .body("currentMemberCount", is(1))
                .body("maxMemberCount", is(nullValue()))
                .body("enrollmentEndDate", is(nullValue()))
                .body("startDate", is(지금.toString()))
                .body("endDate", is(nullValue()))
                .body("owner.id", not(empty()))
                .body("owner.username", is(베루스_이름))
                .body("owner.imageUrl", is(베루스_이미지_URL))
                .body("owner.profileUrl", is(베루스_프로필_URL))
                .body("members", is(empty()))
                .body("tags", is(empty()));
    }
}
