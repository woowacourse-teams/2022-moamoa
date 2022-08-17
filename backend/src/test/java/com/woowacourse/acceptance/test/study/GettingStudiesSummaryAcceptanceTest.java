package com.woowacourse.acceptance.test.study;

import static com.woowacourse.acceptance.fixture.TagFixtures.*;
import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.study.service.response.StudyResponse;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;

@DisplayName("스터디 목록 조회 인수 테스트")
public class GettingStudiesSummaryAcceptanceTest extends AcceptanceTest {

    private Long javaStudyId;
    private Long reactStudyId;
    private Long javascriptStudyId;
    private Long httpStudyId;
    private Long algorithmStudyId;
    private Long linuxStudyId;

    private TagSummaryData javaTag;
    private TagSummaryData fourTag;
    private TagSummaryData beTag;
    private TagSummaryData feTag;
    private TagSummaryData reactTag;

    @BeforeEach
    void initDataBase() {
        LocalDate 지금 = LocalDate.now();

        javaStudyId = 짱구가().로그인하고().자바_스터디를()
                .시작일자는(지금).태그는(자바_태그_ID, 우테코4기_태그_ID, BE_태그_ID)
                .생성한다();

        reactStudyId = 짱구가().로그인하고().리액트_스터디를()
                .시작일자는(지금).태그는(우테코4기_태그_ID, FE_태그_ID, 리액트_태그_ID)
                .생성한다();

        javascriptStudyId = 짱구가().로그인하고().자바스크립트_스터디를()
                .시작일자는(지금).태그는(우테코4기_태그_ID, FE_태그_ID)
                .생성한다();

        httpStudyId = 짱구가().로그인하고().HTTP_스터디를()
                .시작일자는(지금).태그는(우테코4기_태그_ID)
                .생성한다();

        algorithmStudyId = 짱구가().로그인하고().알고리즘_스터디를()
                .시작일자는(지금).태그는(우테코4기_태그_ID)
                .생성한다();

        linuxStudyId = 짱구가().로그인하고().리눅스_스터디를()
                .시작일자는(지금).태그는(우테코4기_태그_ID, BE_태그_ID)
                .생성한다();

        javaTag = new TagSummaryData(1L, "Java");
        fourTag = new TagSummaryData(2L, "4기");
        beTag = new TagSummaryData(3L, "BE");
        feTag = new TagSummaryData(4L, "FE");
        reactTag = new TagSummaryData(5L, "React");
    }

    @DisplayName("첫번째 페이지의 스터디 목록을 조회 한다.")
    @Test
    public void getFirstPageOfStudies() {
        final StudiesResponse studiesResponse = RestAssured.given(spec).log().all()
                .filter(document("studies/summary"))
                .queryParam("page", 0)
                .queryParam("size", 3)
                .when().log().all()
                .get("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(StudiesResponse.class);

        final List<Long> studyIds = studiesResponse.getStudies().stream()
                .map(StudyResponse::getId)
                .collect(toList());
        final List<List<TagSummaryData>> studyTags = studiesResponse.getStudies().stream()
                .map(StudyResponse::getTags)
                .collect(toList());

        assertAll(
                () -> assertThat(studiesResponse.isHasNext()).isTrue(),
                () -> assertThat(studiesResponse.getStudies()).hasSize(3),
                () -> assertThat(studyIds).contains(linuxStudyId, algorithmStudyId, httpStudyId),
                () -> assertThat(studyTags).containsAnyOf(List.of(fourTag, beTag), List.of(fourTag), List.of(fourTag))
        );
    }

    @DisplayName("마지막 페이지의 스터디 목록을 조회 한다.")
    @Test
    public void getLastPageOfStudies() {
        final StudiesResponse studiesResponse = RestAssured.given(spec).log().all()
                .filter(document("studies/summary"))
                .queryParam("page", 1)
                .queryParam("size", 3)
                .when().log().all()
                .get("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(StudiesResponse.class);

        final List<Long> studyIds = studiesResponse.getStudies().stream()
                .map(StudyResponse::getId)
                .collect(toList());
        final List<List<TagSummaryData>> studyTags = studiesResponse.getStudies().stream()
                .map(StudyResponse::getTags)
                .collect(toList());

        assertAll(
                () -> assertThat(studiesResponse.isHasNext()).isFalse(),
                () -> assertThat(studiesResponse.getStudies()).hasSize(3),
                () -> assertThat(studyIds).contains(javascriptStudyId, reactStudyId, javaStudyId),
                () -> assertThat(studyTags).containsExactlyInAnyOrder(List.of(fourTag, feTag), List.of(fourTag, feTag, reactTag), List.of(javaTag, fourTag, beTag))
        );
    }

    @DisplayName("잘못된 페이징 정보로 목록을 조회시 400에러를 응답한다.")
    @ParameterizedTest
    @CsvSource({"-1,3", "1,0", "one,1", "1,one"})
    public void response400WhenRequestByInvalidPagingInfo(String page, String size) {
        RestAssured.given(spec).log().all()
                .filter(document("studies/summary"))
                .queryParam("page", page)
                .queryParam("size", size)
                .when().log().all()
                .get("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", not(blankOrNullString()));
    }

    @DisplayName("페이지 정보 없이 목록 조회시 400에러를 응답한다.")
    @Test
    public void getStudiesByDefaultPage() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies?size=5")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", not(blankOrNullString()));
    }

    @DisplayName("사이즈 정보 없이 목록 조회시 400에러를 응답한다.")
    @Test
    public void getStudiesByDefaultSize() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies?page=0")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", not(blankOrNullString()));
    }

    @DisplayName("페이징 정보가 없는 경우에는 기본값을 사용해 스터디 목록을 조회한다.")
    @Test
    public void getStudiesByDefaultPagingInfo() {
        final StudiesResponse studiesResponse = RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(StudiesResponse.class);
        final List<Long> studyIds = studiesResponse.getStudies().stream()
                .map(StudyResponse::getId)
                .collect(toList());
        final List<List<TagSummaryData>> studyTags = studiesResponse.getStudies().stream()
                .map(StudyResponse::getTags)
                .collect(toList());

        assertAll(
                () -> assertThat(studiesResponse.isHasNext()).isTrue(),
                () -> assertThat(studiesResponse.getStudies()).hasSize(5),
                () -> assertThat(studyIds).contains(linuxStudyId, algorithmStudyId, httpStudyId, javascriptStudyId, reactStudyId),
                () -> assertThat(studyTags).containsExactlyInAnyOrder(
                        List.of(fourTag, beTag), List.of(fourTag),
                        List.of(fourTag), List.of(fourTag, feTag), List.of(fourTag, feTag, reactTag)
                )
        );
    }
}
