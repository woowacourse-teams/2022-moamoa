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
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayName("키워드 검색 인수 테스트")
public class SearchingStudiesAcceptanceTest extends AcceptanceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initDataBase() {
        getBearerTokenBySignInOrUp(new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(2L, "greenlawn", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(3L, "dwoo", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(4L, "verus", "https://image", "github.com"));

        final LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, start_date, owner_id) "
                + "VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'OPEN', 'PREPARE', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '" + now + "', '" + now + "', '2021-12-08', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, enrollment_end_date, start_date, end_date, owner_id) "
                + "VALUES (2, 'React 스터디', '리액트 설명', 'react thumbnail', 'OPEN', 'PREPARE', '디우의 뤼액트 스터디입니다.', 4, 5, '" + now + "', '" + now + "', '2021-11-09', '2021-11-10', '2021-12-08', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, owner_id) "
                + "VALUES (3, 'javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'OPEN', 'PREPARE', '그린론의 자바스크립트 접해보기', 3, 20, '" + now + "', '" + now + "', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, max_member_count, created_date, last_modified_date, owner_id) "
                + "VALUES (4, 'HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'CLOSE', 'PREPARE', '디우의 HTTP 정복하기', 5, '" + now + "', '" + now + "', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, created_date, last_modified_date, owner_id, start_date) "
                + "VALUES (5, '알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'CLOSE', 'PREPARE', '알고리즘을 TDD로 풀자의 베루스입니다.', 1, '" + now + "', '" + now + "', 4, '2021-12-06')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, created_date, last_modified_date, owner_id, start_date, enrollment_end_date, end_date) "
                + "VALUES (6, 'Linux 스터디', '리눅스 설명', 'linux thumbnail', 'CLOSE', 'PREPARE', 'Linux를 공부하자의 베루스입니다.', 1, '" + now + "', '" + now + "', 4, '2021-12-06', '2021-12-07', '2022-01-07')");

        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (1, 'generation')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (2, 'area')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (3, 'subject')");

        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (1, 'Java', '자바', 3)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (2, '4기', '우테코4기', 1)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (3, 'BE', '백엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (4, 'FE', '프론트엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (5, 'React', '리액트', 3)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (1, 1)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (1, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (1, 3)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (2, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (2, 4)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (2, 5)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (3, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (3, 4)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (4, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (4, 3)");
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
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies/search")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(true))
                .body("studies", hasSize(5))
                .body("studies.id", contains(
                        notNullValue(), notNullValue(), notNullValue(), notNullValue(), notNullValue()))
                .body("studies.title", contains(
                        "Java 스터디", "React 스터디", "javaScript 스터디", "HTTP 스터디", "알고리즘 스터디"))
                .body("studies.excerpt", contains(
                        "자바 설명", "리액트 설명", "자바스크립트 설명", "HTTP 설명", "알고리즘 설명"))
                .body("studies.thumbnail", contains(
                        "java thumbnail", "react thumbnail", "javascript thumbnail", "http thumbnail",
                        "algorithm thumbnail"))
                .body("studies.recruitStatus", contains("OPEN", "OPEN", "OPEN", "CLOSE", "CLOSE"));
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
                .body("studies.title", contains("Java 스터디", "javaScript 스터디"))
                .body("studies.excerpt", contains("자바 설명", "자바스크립트 설명"))
                .body("studies.thumbnail", contains("java thumbnail", "javascript thumbnail"))
                .body("studies.recruitStatus", contains("OPEN", "OPEN"));
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
                .body("studies.recruitStatus", contains("OPEN"));
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
                .body("studies.title", contains("Java 스터디", "HTTP 스터디"))
                .body("studies.excerpt", contains("자바 설명", "HTTP 설명"))
                .body("studies.thumbnail", contains("java thumbnail", "http thumbnail"))
                .body("studies.recruitStatus", contains("OPEN", "CLOSE"));
    }

    @DisplayName("필터로 필터링한 내용과 제목 검색을 함께 조합해 스터디 목록을 조회한다.")
    @Test
    public void getStudiesByFilterAndTitle() {
        RestAssured.given().log().all()
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
                .body("studies.recruitStatus", contains("OPEN"));
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
                .body("studies.title", contains("Java 스터디", "React 스터디", "javaScript 스터디", "HTTP 스터디"))
                .body("studies.excerpt", contains("자바 설명", "리액트 설명", "자바스크립트 설명", "HTTP 설명"))
                .body("studies.thumbnail",
                        contains("java thumbnail", "react thumbnail", "javascript thumbnail", "http thumbnail"))
                .body("studies.recruitStatus", contains("OPEN", "OPEN", "OPEN", "CLOSE"));
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
                .body("studies.recruitStatus", contains("OPEN"));
    }
}
