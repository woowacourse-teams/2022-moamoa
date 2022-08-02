package com.woowacourse.acceptance.study;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class GettingStudyDetailsAcceptanceTest extends AcceptanceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initDataBase() {
        getBearerTokenBySignInOrUp(new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(2L, "greenlawn", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(3L, "dwoo", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(4L, "verus", "https://image", "github.com"));

        final LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, start_date, owner_id) "
                + "VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'RECRUITMENT_START', 'PREPARE', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '" + now + "', '" + now + "', '2021-12-08', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, enrollment_end_date, start_date, end_date, owner_id) "
                + "VALUES (2, 'React 스터디', '리액트 설명', 'react thumbnail', 'RECRUITMENT_START', 'PREPARE', '디우의 뤼액트 스터디입니다.', 4, 5, '" + now + "', '" + now + "', '2021-11-09', '2021-11-10', '2021-12-08', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, owner_id) "
                + "VALUES (3, 'javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'RECRUITMENT_START', 'PREPARE', '그린론의 자바스크립트 접해보기', 3, 20, '" + now + "', '" + now + "', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, max_member_count, created_date, last_modified_date, owner_id) "
                + "VALUES (4, 'HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'RECRUITMENT_END', 'PREPARE', '디우의 HTTP 정복하기', 5, '" + now + "', '" + now + "', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, created_date, last_modified_date, owner_id, start_date) "
                + "VALUES (5, '알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'RECRUITMENT_END', 'PREPARE', '알고리즘을 TDD로 풀자의 베루스입니다.', 1, '" + now + "', '" + now + "', 4, '2021-12-06')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, created_date, last_modified_date, owner_id, start_date, enrollment_end_date, end_date) "
                + "VALUES (6, 'Linux 스터디', '리눅스 설명', 'linux thumbnail', 'RECRUITMENT_END', 'PREPARE', 'Linux를 공부하자의 베루스입니다.', 1, '" + now + "', '" + now + "', 4, '2021-12-06', '2021-12-07', '2022-01-07')");

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

    @DisplayName("스터디 요약 정보 외에 상세 정보를 포함하여 조회할 수 있다.")
    @Test
    public void getStudyDetails() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies/2")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(empty()))
                .body("title", is("React 스터디"))
                .body("excerpt", is("리액트 설명"))
                .body("thumbnail", is("react thumbnail"))
                .body("recruitmentStatus", is("RECRUITMENT_START"))
                .body("description", is("디우의 뤼액트 스터디입니다."))
                .body("currentMemberCount", is(4))
                .body("maxMemberCount", is("5"))
                .body("enrollmentEndDate", is("2021-11-09"))
                .body("startDate", is("2021-11-10"))
                .body("endDate", is("2021-12-08"))
                .body("owner.id", is(3))
                .body("owner.username", is("dwoo"))
                .body("owner.imageUrl", is("https://image"))
                .body("owner.profileUrl", is("github.com"))
                .body("members.id", not(empty()))
                .body("members.username", contains("jjanggu", "greenlawn", "verus"))
                .body("members.imageUrl", contains("https://image", "https://image", "https://image"))
                .body("members.profileUrl", contains("github.com", "github.com", "github.com"))
                .body("tags.id", not(empty()))
                .body("tags.name", contains("4기", "FE", "React"));
    }

    @DisplayName("선택 데이터가 없는 스터디 세부사항을 조회한다.")
    @Test
    public void getNotHasOptionalDataStudyDetails() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies/5")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(5))
                .body("title", is("알고리즘 스터디"))
                .body("excerpt", is("알고리즘 설명"))
                .body("thumbnail", is("algorithm thumbnail"))
                .body("recruitmentStatus", is("RECRUITMENT_END"))
                .body("description", is("알고리즘을 TDD로 풀자의 베루스입니다."))
                .body("currentMemberCount", is(1))
                .body("maxMemberCount", is(""))
                .body("enrollmentEndDate", is(""))
                .body("startDate", is("2021-12-06"))
                .body("endDate", is(""))
                .body("owner.id", is(4))
                .body("owner.username", is("verus"))
                .body("owner.imageUrl", is("https://image"))
                .body("owner.profileUrl", is("github.com"))
                .body("members", is(empty()))
                .body("tags", is(empty()));
    }
}
