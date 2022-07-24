package com.woowacourse.acceptance.study;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.study.controller.request.OpenStudyRequest;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("스터디 목록 조회 인수 테스트")
public class GettingStudiesSummaryAcceptanceTest extends AcceptanceTest {

    private int javaStudyId;
    private int reactStudyId;
    private int javascriptStudyId;
    private int httpStudyId;
    private int algorithmStudyId;
    private int linuxStudyId;

    @BeforeEach
    void initDataBase() {
        String token = getBearerTokenBySignInOrUp(new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));

        OpenStudyRequest javaRequest = OpenStudyRequest.builder()
                .title("Java 스터디").excerpt("자바 설명").thumbnail("java thumbnail")
                .description("그린론의 우당탕탕 자바 스터디입니다.").startDate(LocalDate.now().plusDays(1))
                .build();
        javaStudyId = (int) createStudy(token, javaRequest);

        OpenStudyRequest reactRequest = OpenStudyRequest.builder()
                .title("React 스터디").excerpt("리액트 설명").thumbnail("react thumbnail")
                .description("디우의 뤼액트 스터디입니다.").startDate(LocalDate.now().plusDays(2))
                .build();
        reactStudyId = (int) createStudy(token, reactRequest);

        OpenStudyRequest javascriptRequest = OpenStudyRequest.builder()
                .title("javaScript 스터디").excerpt("자바스크립트 설명").thumbnail("javascript thumbnail")
                .description("그린론의 자바스크립트 접해보기").startDate(LocalDate.now().plusDays(3))
                .build();
        javascriptStudyId = (int) createStudy(token, javascriptRequest);

        OpenStudyRequest httpRequest = OpenStudyRequest.builder()
                .title("HTTP 스터디").excerpt("HTTP 설명").thumbnail("http thumbnail")
                .description("디우의 HTTP 정복하기").startDate(LocalDate.now().plusDays(3))
                .build();
        httpStudyId = (int) createStudy(token, httpRequest);

        OpenStudyRequest algorithmRequest = OpenStudyRequest.builder()
                .title("알고리즘 스터디").excerpt("알고리즘 설명").thumbnail("algorithm thumbnail")
                .description("알고리즘을 TDD로 풀자의 베루스입니다.").startDate(LocalDate.now().plusDays(2))
                .build();
        algorithmStudyId = (int) createStudy(token, algorithmRequest);

        OpenStudyRequest linuxRequest = OpenStudyRequest.builder()
                .title("Linux 스터디").excerpt("리눅스 설명").thumbnail("linux thumbnail")
                .description("Linux를 공부하자의 베루스입니다.").startDate(LocalDate.now().plusDays(2))
                .build();
        linuxStudyId = (int) createStudy(token, linuxRequest);
    }

    @DisplayName("첫번째 페이지의 스터디 목록을 조회 한다.")
    @Test
    public void getFirstPageOfStudies() {
        페이징을_통한_스터디_목록_조회(0, 3)
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(true))
                .body("studies", hasSize(3))
                .body("studies.id", contains(javaStudyId, reactStudyId, javascriptStudyId))
                .body("studies.title", contains("Java 스터디", "React 스터디", "javaScript 스터디"))
                .body("studies.excerpt", contains("자바 설명", "리액트 설명", "자바스크립트 설명"))
                .body("studies.thumbnail",
                        contains("java thumbnail", "react thumbnail", "javascript thumbnail"))
                .body("studies.status", contains("OPEN", "OPEN", "OPEN"));
    }

    @DisplayName("마지막 페이지의 스터디 목록을 조회 한다.")
    @Test
    public void getLastPageOfStudies() {
        페이징을_통한_스터디_목록_조회(1, 3)
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(false))
                .body("studies", hasSize(3))
                .body("studies.id", contains(httpStudyId, algorithmStudyId, linuxStudyId))
                .body("studies.title", contains("HTTP 스터디", "알고리즘 스터디", "Linux 스터디"))
                .body("studies.excerpt", contains("HTTP 설명", "알고리즘 설명", "리눅스 설명"))
                .body("studies.thumbnail", contains("http thumbnail", "algorithm thumbnail", "linux thumbnail"))
                .body("studies.status", contains("OPEN", "OPEN", "OPEN"));
    }

    @DisplayName("잘못된 페이징 정보로 목록을 조회시 400에러를 응답한다.")
    @ParameterizedTest
    @CsvSource({"-1,3", "1,0", "one,1", "1,one"})
    public void response400WhenRequestByInvalidPagingInfo(String page, String size) {
        페이징을_통한_스터디_목록_조회(page, size)
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
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(true))
                .body("studies", hasSize(5))
                .body("studies.id",
                        contains(javaStudyId, reactStudyId, javascriptStudyId, httpStudyId, algorithmStudyId)
                )
                .body("studies.title", contains(
                        "Java 스터디", "React 스터디", "javaScript 스터디", "HTTP 스터디", "알고리즘 스터디"))
                .body("studies.excerpt", contains(
                        "자바 설명", "리액트 설명", "자바스크립트 설명", "HTTP 설명", "알고리즘 설명"))
                .body("studies.thumbnail", contains(
                        "java thumbnail", "react thumbnail", "javascript thumbnail", "http thumbnail",
                        "algorithm thumbnail"))
                .body("studies.status", contains("OPEN", "OPEN", "OPEN", "OPEN", "OPEN"));
    }

    private ValidatableResponse 페이징을_통한_스터디_목록_조회(Object page, Object size) {
        return RestAssured.given().log().all()
                .queryParam("page", page)
                .queryParam("size", size)
                .when().log().all()
                .get("/api/studies")
                .then().log().all();
    }
}
