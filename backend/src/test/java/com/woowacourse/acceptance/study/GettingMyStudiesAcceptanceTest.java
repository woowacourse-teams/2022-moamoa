package com.woowacourse.acceptance.study;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class GettingMyStudiesAcceptanceTest extends AcceptanceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initDatabase() {
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (1, 1, 'jjanggu', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (2, 2, 'greenlawn', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (3, 3, 'dwoo', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (4, 4, 'verus', 'https://image', 'github.com')");

        final LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_at, start_date, owner_id) "
                + "VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'RECRUITMENT_START', 'PREPARE', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '" + now + "', '2021-12-08', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_at, enrollment_end_date, start_date, end_date, owner_id) "
                + "VALUES (2, 'React 스터디', '리액트 설명', 'react thumbnail', 'RECRUITMENT_START', 'PREPARE', '디우의 뤼액트 스터디입니다.', 4, 5, '" + now + "', '2021-11-09', '2021-11-10', '2021-12-08', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_at, start_date, owner_id) "
                + "VALUES (3, 'javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'RECRUITMENT_START', 'PREPARE', '그린론의 자바스크립트 접해보기', 3, 20, '" + now + "', '2022-08-03', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, max_member_count, created_at, start_date, owner_id) "
                + "VALUES (4, 'HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'RECRUITMENT_END', 'PREPARE', '디우의 HTTP 정복하기', 5, '" + now + "', '2022-08-03', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, created_at, owner_id, start_date) "
                + "VALUES (5, '알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'RECRUITMENT_END', 'PREPARE', '알고리즘을 TDD로 풀자의 베루스입니다.', 1, '" + now + "', 4, '2021-12-06')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, created_at, owner_id, start_date, enrollment_end_date, end_date) "
                + "VALUES (6, 'Linux 스터디', '리눅스 설명', 'linux thumbnail', 'RECRUITMENT_END', 'PREPARE', 'Linux를 공부하자의 베루스입니다.', 1, '" + now + "', 4, '2021-12-06', '2021-12-07', '2022-01-07')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_at, owner_id, start_date, enrollment_end_date, end_date) "
                + "VALUES (7, 'OS 스터디', 'OS 설명', 'os thumbnail', 'RECRUITMENT_END', 'PREPARE', 'OS를 공부하자의 베루스입니다.', 1, 6, '" + now + "', 4, '2021-12-06', '2021-12-07', '2022-01-07')");

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

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 3)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 4)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (2, 1)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (2, 2)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (2, 4)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (3, 3)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (3, 4)");
    }

    @DisplayName("내가 참여한 스터디를 조회한다.")
    @Test
    void getMyStudies() {
        final String token = getBearerTokenBySignInOrUp(new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));

        RestAssured.given(spec).log().all()
                .filter(document("studies/myStudy"))
                .header(HttpHeaders.AUTHORIZATION, token)
                .when().log().all()
                .get("/api/my/studies")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("studies[0].id", is(2))
                .body("studies[0].title", is("React 스터디"))
                .body("studies[0].studyStatus", is("PREPARE"))
                .body("studies[0].currentMemberCount", is(4))
                .body("studies[0].maxMemberCount", is(5))
                .body("studies[0].startDate", is("2021-11-10"))
                .body("studies[0].endDate", is("2021-12-08"))
                .body("studies[0].owner.username", is("dwoo"))
                .body("studies[0].owner.imageUrl", is("https://image"))
                .body("studies[0].owner.profileUrl", is("github.com"))
                .body("studies[0].tags.id", contains(2, 4, 5))
                .body("studies[0].tags.name", contains("4기", "FE", "React"));
    }

    @Test
    @DisplayName("특정 스터디에서 나의 Role을 확인한다.")
    void isMyStudy() {
        final String token = getBearerTokenBySignInOrUp(new GithubProfileResponse(4L, "verus", "https://image", "github.com"));

        RestAssured.given(spec).log().all()
                .filter(document("members/me/role"))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .queryParam("study-id", 7)
                .when()
                .get("/api/members/me/role")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("role", is("OWNER"));
    }
}
