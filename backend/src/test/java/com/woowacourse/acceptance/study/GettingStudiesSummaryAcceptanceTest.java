package com.woowacourse.acceptance.study;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.study.service.StudyResponse;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayName("스터디 목록 조회 인수 테스트")
public class GettingStudiesSummaryAcceptanceTest extends AcceptanceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (1, 'generation')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (2, 'area')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (3, 'subject')");

        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (1, 'Java', '자바', 3)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (2, '4기', '우테코4기', 1)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (3, 'BE', '백엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (4, 'FE', '프론트엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (5, 'React', '리액트', 3)");

        String token = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));

        CreatingStudyRequest javaRequest = CreatingStudyRequest.builder()
                .title("Java 스터디").excerpt("자바 설명").thumbnail("java thumbnail")
                .description("그린론의 우당탕탕 자바 스터디입니다.").startDate(LocalDate.now().plusDays(1))
                .tagIds(List.of(1L, 2L, 3L))
                .build();
        javaStudyId = createStudy(token, javaRequest);

        CreatingStudyRequest reactRequest = CreatingStudyRequest.builder()
                .title("React 스터디").excerpt("리액트 설명").thumbnail("react thumbnail")
                .description("디우의 뤼액트 스터디입니다.").startDate(LocalDate.now().plusDays(2))
                .tagIds(List.of(2L, 4L, 5L))
                .build();
        reactStudyId = createStudy(token, reactRequest);

        CreatingStudyRequest javascriptRequest = CreatingStudyRequest.builder()
                .title("javaScript 스터디").excerpt("자바스크립트 설명").thumbnail("javascript thumbnail")
                .description("그린론의 자바스크립트 접해보기").startDate(LocalDate.now().plusDays(3))
                .tagIds(List.of(2L, 4L))
                .build();
        javascriptStudyId = createStudy(token, javascriptRequest);

        CreatingStudyRequest httpRequest = CreatingStudyRequest.builder()
                .title("HTTP 스터디").excerpt("HTTP 설명").thumbnail("http thumbnail")
                .description("디우의 HTTP 정복하기").startDate(LocalDate.now().plusDays(3))
                .tagIds(List.of(2L))
                .build();
        httpStudyId = createStudy(token, httpRequest);

        CreatingStudyRequest algorithmRequest = CreatingStudyRequest.builder()
                .title("알고리즘 스터디").excerpt("알고리즘 설명").thumbnail("algorithm thumbnail")
                .description("알고리즘을 TDD로 풀자의 베루스입니다.").startDate(LocalDate.now().plusDays(2))
                .tagIds(List.of(2L))
                .build();
        algorithmStudyId = createStudy(token, algorithmRequest);

        CreatingStudyRequest linuxRequest = CreatingStudyRequest.builder()
                .title("Linux 스터디").excerpt("리눅스 설명").thumbnail("linux thumbnail")
                .description("Linux를 공부하자의 베루스입니다.").startDate(LocalDate.now().plusDays(2))
                .tagIds(List.of(2L, 3L))
                .build();
        linuxStudyId = createStudy(token, linuxRequest);

        javaTag = new TagSummaryData(1L, "Java");
        fourTag = new TagSummaryData(2L, "4기");
        beTag = new TagSummaryData(3L, "BE");
        feTag = new TagSummaryData(4L, "FE");
        reactTag = new TagSummaryData(5L, "React");
    }

    @DisplayName("첫번째 페이지의 스터디 목록을 조회 한다.")
    @Test
    public void getFirstPageOfStudies() {
        final StudiesResponse studiesResponse = 페이징을_통한_스터디_목록_조회(0, 3)
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
                () -> assertThat(studyIds).contains(javaStudyId, reactStudyId, javascriptStudyId),
                () -> assertThat(studyTags).containsAnyOf(List.of(javaTag, fourTag, beTag), List.of(fourTag, feTag, reactTag), List.of(feTag, reactTag))
        );
    }

    @DisplayName("마지막 페이지의 스터디 목록을 조회 한다.")
    @Test
    public void getLastPageOfStudies() {
        final StudiesResponse studiesResponse = 페이징을_통한_스터디_목록_조회(1, 3)
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
                () -> assertThat(studyIds).contains(httpStudyId, algorithmStudyId, linuxStudyId),
                () -> assertThat(studyTags).containsExactlyInAnyOrder(List.of(fourTag, beTag), List.of(fourTag), List.of(fourTag))
        );
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
                () -> assertThat(studyIds).contains(javaStudyId, reactStudyId, javascriptStudyId, httpStudyId,
                        algorithmStudyId),
                () -> assertThat(studyTags).containsExactlyInAnyOrder(
                        List.of(javaTag, fourTag, beTag), List.of(fourTag, feTag, reactTag),
                        List.of(fourTag, feTag), List.of(fourTag), List.of(fourTag)
                )
        );
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
