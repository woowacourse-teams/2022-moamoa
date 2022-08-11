package com.woowacourse.acceptance.test.study;

import static com.woowacourse.acceptance.fixture.TagFixtures.BE_태그_ID;
import static com.woowacourse.acceptance.fixture.TagFixtures.FE_태그_ID;
import static com.woowacourse.acceptance.fixture.TagFixtures.리액트_태그_ID;
import static com.woowacourse.acceptance.fixture.TagFixtures.우테코4기_태그_ID;
import static com.woowacourse.acceptance.fixture.TagFixtures.자바_태그_ID;
import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;

@DisplayName("키워드 검색 인수 테스트")
public class SearchingStudiesAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUp() {
        LocalDate 지금 = LocalDate.now();

         짱구가().로그인하고().자바_스터디를()
                .시작일자는(지금).태그는(자바_태그_ID, 우테코4기_태그_ID, BE_태그_ID)
                .생성한다();

         짱구가().로그인하고().리액트_스터디를()
                .시작일자는(지금).태그는(우테코4기_태그_ID, FE_태그_ID, 리액트_태그_ID)
                .생성한다();

         짱구가().로그인하고().자바스크립트_스터디를()
                .시작일자는(지금).태그는(우테코4기_태그_ID, FE_태그_ID)
                .생성한다();

         짱구가().로그인하고().HTTP_스터디를()
                .시작일자는(지금).태그는(우테코4기_태그_ID, BE_태그_ID)
                .생성한다();

         짱구가().로그인하고().알고리즘_스터디를()
                .시작일자는(지금)
                .생성한다();

         짱구가().로그인하고().리눅스_스터디를()
                .시작일자는(지금)
                .생성한다();
    }

    @DisplayName("잘못된 페이징 정보로 목록을 검색시 400에러를 응답한다.")
    @ParameterizedTest
    @CsvSource({"-1,3", "1,0", "one,1", "1,one"})
    public void response400WhenRequestByInvalidPagingInfo(String page, String size) {
        RestAssured.given().log().all()
                .queryParam("title", "java")
                .queryParam("page", page)
                .queryParam("size", size)
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", not(blankOrNullString()));
    }

    @DisplayName("페이지 정보 없이 목록 검색시 400에러를 응답한다.")
    @Test
    public void getStudiesByDefaultPage() {
        RestAssured.given().log().all()
                .queryParam("title", "java")
                .queryParam("size", 5)
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", not(blankOrNullString()));
    }

    @DisplayName("사이즈 정보 없이 목록 조회시 400에러를 응답한다.")
    @Test
    public void getStudiesByDefaultSize() {
        RestAssured.given().log().all()
                .queryParam("title", "java")
                .queryParam("page", 0)
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", not(blankOrNullString()));
    }

    @DisplayName("페이징 정보 및 키워드가 없는 경우에는 기본페이징 정보를 사용해 전체 스터디 목록에서 조회한다.")
    @Test
    public void getStudiesByDefaultPagingInfo() {
        RestAssured.given(spec).log().all()
                .filter(document("studies/search"))
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(true))
                .body("studies", hasSize(5))
                .body("studies.id", not(empty()))
                .body("studies.title", contains(
                        "Linux 스터디", "알고리즘 스터디", "HTTP 스터디", "javaScript 스터디", "React 스터디"))
                .body("studies.excerpt", contains(
                        "리눅스 설명", "알고리즘 설명", "HTTP 설명", "자바스크립트 설명", "리액트 설명"))
                .body("studies.thumbnail", contains(
                        "linux thumbnail", "algorithm thumbnail", "http thumbnail", "javascript thumbnail",
                        "react thumbnail"))
                .body("studies.recruitmentStatus", contains("RECRUITMENT_START", "RECRUITMENT_START", "RECRUITMENT_START", "RECRUITMENT_START", "RECRUITMENT_START"));
    }

    @DisplayName("앞뒤 공백을 제거한 키워드로 스터디 목록을 조회한다.")
    @Test
    void getStudiesByTrimKeyword() {
        RestAssured.given().log().all()
                .queryParam("title", "   java   ")
                .queryParam("page", 0)
                .queryParam("size", 3)
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(false))
                .body("studies", hasSize(2))
                .body("studies.id", contains(notNullValue(), notNullValue()))
                .body("studies.title", contains("javaScript 스터디", "Java 스터디"))
                .body("studies.excerpt", contains("자바스크립트 설명", "자바 설명"))
                .body("studies.thumbnail", contains("javascript thumbnail", "java thumbnail"))
                .body("studies.recruitmentStatus", contains("RECRUITMENT_START", "RECRUITMENT_START"));
    }

    @DisplayName("중간에 공백이 있는 키워드를 사용해 스터디 목록을 조회한다.")
    @Test
    void getStudiesByHasSpaceKeyword() {
        RestAssured.given().log().all()
                .queryParam("title", "Java 스터디")
                .queryParam("page", 0)
                .queryParam("size", 3)
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(false))
                .body("studies", hasSize(1))
                .body("studies.id", contains(notNullValue()))
                .body("studies.title", contains("Java 스터디"))
                .body("studies.excerpt", contains("자바 설명"))
                .body("studies.thumbnail", contains("java thumbnail"))
                .body("studies.recruitmentStatus", contains("RECRUITMENT_START"));
    }

    @DisplayName("필터로 필터링하여 스터디 목록을 조회한다.")
    @Test
    public void getStudiesByFilter() {
        RestAssured.given().log().all()
                .queryParam("title", "")
                .queryParam("area", 3)
                .queryParam("page", 0)
                .queryParam("size", 3)
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(false))
                .body("studies", hasSize(2))
                .body("studies.id", not(empty()))
                .body("studies.title", contains("HTTP 스터디", "Java 스터디"))
                .body("studies.excerpt", contains("HTTP 설명", "자바 설명"))
                .body("studies.thumbnail", contains("http thumbnail", "java thumbnail"))
                .body("studies.recruitmentStatus", contains("RECRUITMENT_START", "RECRUITMENT_START"));
    }

    @DisplayName("필터로 필터링한 내용과 제목 검색을 함께 조합해 스터디 목록을 조회한다.")
    @Test
    public void getStudiesByFilterAndTitle() {
        RestAssured.given(spec).log().all()
                .filter(document("studies/searchWithTags"))
                .queryParam("title", "ja")
                .queryParam("area", 3)
                .queryParam("page", 0)
                .queryParam("size", 3)
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(false))
                .body("studies", hasSize(1))
                .body("studies.id", contains(notNullValue()))
                .body("studies.title", contains("Java 스터디"))
                .body("studies.excerpt", contains("자바 설명"))
                .body("studies.thumbnail", contains("java thumbnail"))
                .body("studies.recruitmentStatus", contains("RECRUITMENT_START"));
    }

    @DisplayName("같은 카테고리의 필터로 필터링하여 스터디 목록을 조회한다.")
    @Test
    public void getStudiesBySameCategoryFilter() {
        RestAssured.given().log().all()
                .queryParam("title", "")
                .queryParam("area", 3)
                .queryParam("area", 4)
                .queryParam("page", 0)
                .queryParam("size", 5)
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(false))
                .body("studies", hasSize(4))
                .body("studies.id", not(empty()))
                .body("studies.title", contains("HTTP 스터디", "javaScript 스터디", "React 스터디", "Java 스터디"))
                .body("studies.excerpt", contains("HTTP 설명", "자바스크립트 설명", "리액트 설명", "자바 설명"))
                .body("studies.thumbnail",
                        contains("http thumbnail", "javascript thumbnail", "react thumbnail", "java thumbnail"))
                .body("studies.recruitmentStatus", contains("RECRUITMENT_START", "RECRUITMENT_START", "RECRUITMENT_START", "RECRUITMENT_START"));
    }

    @DisplayName("서로 다른 카테고리의 필터로 필터링하여 스터디 목록을 조회한다.")
    @Test
    public void getStudiesByAnotherCategoryFilter() {
        RestAssured.given().log().all()
                .queryParam("title", "")
                .queryParam("area", 3)
                .queryParam("subject", 1)
                .queryParam("tag", 1)
                .queryParam("page", 0)
                .queryParam("size", 3)
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(false))
                .body("studies", hasSize(1))
                .body("studies.id", contains(notNullValue()))
                .body("studies.title", contains("Java 스터디"))
                .body("studies.excerpt", contains("자바 설명"))
                .body("studies.thumbnail", contains("java thumbnail"))
                .body("studies.recruitmentStatus", contains("RECRUITMENT_START"));
    }
}
