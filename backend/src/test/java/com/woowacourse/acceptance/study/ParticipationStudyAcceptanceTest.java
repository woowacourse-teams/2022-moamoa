package com.woowacourse.acceptance.study;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class ParticipationStudyAcceptanceTest extends AcceptanceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initDataBase() {
        getBearerTokenBySignInOrUp(new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(2L, "greenlawn", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(3L, "dwoo", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(4L, "verus", "https://image", "github.com"));

        jdbcTemplate.update(
                "INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_at, start_date, owner_id) "
                        + "VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'OPEN', 'PREPARE', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '2021-11-08T11:58:20.551705', '2021-12-08T11:58:20.657123', 2)");

        jdbcTemplate.update(
                "INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_at, enrollment_end_date, start_date, end_date, owner_id) "
                        + "VALUES (2, 'React 스터디', '리액트 설명', 'react thumbnail', 'CLOSE', 'PREPARE', '디우의 뤼액트 스터디입니다.', 4, 5, '1998-11-08T11:58:20.551705', '1999-01-01T00:00:00', '2021-11-10T11:58:20.551705', '2021-12-08T11:58:20.551705', 3)");

        jdbcTemplate.update(
                "INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_at, start_date, owner_id) "
                        + "VALUES (3, 'Java 스터디', '자바 설명', 'java thumbnail', 'CLOSE', 'PREPARE', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '2021-11-08T11:58:20.551705', '2021-12-08T11:58:20.657123', 2)");

        jdbcTemplate.update(
                "INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_at, start_date, owner_id) "
                        + "VALUES (4, 'Java 스터디', '자바 설명', 'java thumbnail', 'CLOSE', 'PREPARE', '그린론의 우당탕탕 자바 스터디입니다.', 3, 3, '2021-11-08T11:58:20.551705', '2021-12-08T11:58:20.657123', 2)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 1)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 4)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (2, 1)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (2, 2)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (3, 1)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (3, 4)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 1)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 4)");
    }

    @DisplayName("아직 스터디에 가입되지 않은 회원은 스터디에 참여가 가능하다.")
    @Test
    public void participateStudy() {
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(3L, "dwoo", "https://image", "github.com"));

        RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, jwtToken)
                .when().log().all()
                .post("/api/studies/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("한 명의 회원은 동일한 스터디에 대해서 한 번만 가입이 가능하다.")
    @Test
    public void participateOnlyOnce() {
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));

        RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, jwtToken)
                .when().log().all()
                .post("/api/studies/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("모집기간이 이미 만료된 스터디에 신청시에는 에러가 발생한다.")
    @Test
    public void get400WithEndRecruit() {
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(4L, "verus", "https://image", "github.com"));

        RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, jwtToken)
                .when().log().all()
                .post("/api/studies/2")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("CLOSE 상태의 스터디에 참여 요청시 400 에러가 발생한다.")
    @Test
    public void participateCloseStudy() {
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(3L, "dwoo", "https://image", "github.com"));

        RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, jwtToken)
                .when().log().all()
                .post("/api/studies/3")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("인원이 가득찬 스터디에 참여하면 400 에러가 발생한다.")
    @Test
    public void participateFullStudy() {
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(3L, "dwoo", "https://image", "github.com"));

        RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, jwtToken)
                .when().log().all()
                .post("/api/studies/4")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("스터디장(owner)은 스터디에 참여할 수 없다.")
    @Test
    public void participateOwner() {
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(2L, "greenlawn", "https://image", "github.com"));

        RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, jwtToken)
                .when().log().all()
                .post("/api/studies/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
