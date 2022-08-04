package com.woowacourse.acceptance.study;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

public class CreatingStudyAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void initDataBase() {
        getBearerTokenBySignInOrUp(new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(2L, "greenlawn", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(3L, "dwoo", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(4L, "verus", "https://image", "github.com"));
    }

    @DisplayName("유효하지 않은 토큰으로 스터디 개설 시 401을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"Invalid Token", "bearer Invalid Token"})
    void get401WhenUsingInvalidToken(String invalidToken) {
        RestAssured
                .given().log().all()
                .header(AUTHORIZATION, invalidToken)
                .when().log().all()
                .post("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("헤더에 토큰 없이 스터디 개설 시 401을 반환한다.")
    @Test
    void get401WhenUsingEmptyToken() {
        RestAssured
                .given().log().all()
                .when().log().all()
                .post("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("스터디 개설 시 필수 데이터가 없는 경우 400을 반환한다.")
    @ParameterizedTest
    @MethodSource("provideBlankForRequiredFields")
    void get400WhenSetBlankToRequiredField(Map<String, String> param) {
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));

        RestAssured
                .given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, jwtToken)
                .body(param)
                .when().log().all()
                .post("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    public static Stream<Arguments> provideBlankForRequiredFields() {
        return Stream.of(
                Arguments.of(Map.of(
                        "title", "", "excerpt", "자바를 공부하는 스터디", "thumbnail", "image",
                        "description", "스터디 상세 설명입니다.", "startDate", "2022-07-12")),
                Arguments.of(Map.of(
                        "excerpt", "자바를 공부하는 스터디", "thumbnail", "image",
                        "description", "스터디 상세 설명입니다.", "startDate", "2022-07-12")),
                Arguments.of(Map.of(
                        "title", "제목", "excerpt", "    \t     ", "thumbnail", "image",
                        "description", "스터디 상세 설명입니다.", "startDate", "2022-07-12")),
                Arguments.of(Map.of(
                        "title", "제목", "excerpt", "자바를 공부하는 스터디", "thumbnail", "    \n      ",
                        "description", "스터디 상세 설명입니다.", "startDate", "2022-07-12")),
                Arguments.of(Map.of(
                        "title", "제목", "excerpt", "자바를 공부하는 스터디", "thumbnail", "image",
                        "description", "     \t \n  ", "startDate", "2022-07-12")),
                Arguments.of(Map.of(
                        "title", "제목", "excerpt", "자바를 공부하는 스터디", "thumbnail", "image",
                        "description", "스터디 상세 설명입니다.", "startDate", "")),
                Arguments.of(Map.of(
                        "title", "제목", "excerpt", "자바를 공부하는 스터디", "thumbnail", "image",
                        "description", "스터디 상세 설명입니다.", "startDate", "startdate"))
        );
    }

    @DisplayName("스터디 생성 시 선택 데이터의 형식이 유효하지 않는 경우 400을 반환한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidFormatForOptionalFields")
    void get400WhenSetInvalidFormatToOptionalFields(Map<String, String> optionalBody) {
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));

        Map<String, String> requiredBody = Map.of("title", "제목", "excerpt", "자바를 공부하는 스터디",
                "thumbnail", "image", "description", "스터디 상세 설명입니다.", "startDate", "2022-07-20");
        final HashMap<String, String> body = new HashMap<>(requiredBody);
        body.putAll(optionalBody);

        RestAssured
                .given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, jwtToken)
                .body(body)
                .when().log().all()
                .post("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    public static Stream<Arguments> provideInvalidFormatForOptionalFields() {
        return Stream.of(
                Arguments.of(Map.of("maxMemberCount", "one")),
                Arguments.of(Map.of("maxMemberCount", "0")),
                Arguments.of(Map.of("enrollmentEndDate", "2022-seven-one")),
                Arguments.of(Map.of("endDate", "2022 07 11")),
                Arguments.of(Map.of("tagIds", List.of("one", "two")))
        );
    }

    @Test
    @DisplayName("정상적인 스터디 생성")
    void createStudy() {
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));

        final String location = RestAssured.given(spec).log().all()
                .filter(document("studies/create"))
                .header(AUTHORIZATION, jwtToken)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Map.of("title", "제목", "excerpt", "자바를 공부하는 스터디", "thumbnail", "image",
                        "description", "스터디 상세 설명입니다.", "startDate", LocalDate.now().plusDays(5).format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")), "endDate", ""))
                .when().log().all()
                .post("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header(LOCATION);

        assertThat(location).matches(Pattern.compile("/api/studies/\\d+"));
    }
}
