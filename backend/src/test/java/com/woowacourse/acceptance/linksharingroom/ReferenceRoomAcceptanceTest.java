package com.woowacourse.acceptance.linksharingroom;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import com.woowacourse.moamoa.studyroom.linksharingroom.service.request.CreatingLinkRequest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReferenceRoomAcceptanceTest extends AcceptanceTest {

    private static final GithubProfileResponse JJANGGU = new GithubProfileResponse(1L, "jjanggu", "https://image",
            "github.com");

    private Long javaStudyId;

    @BeforeEach
    void initDataBase() {
        final String jjangguToken = getBearerTokenBySignInOrUp(JJANGGU);

        final LocalDate startDate = LocalDate.now();
        CreatingStudyRequest javaStudyRequest = CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();

        javaStudyId = createStudy(jjangguToken, javaStudyRequest);
    }

    @DisplayName("참여한 스터디의 링크 공유실에 정상적으로 글을 작성한다.")
    @Test
    void shareLink() {
        final String jwtToken = getBearerTokenBySignInOrUp(JJANGGU);
        final CreatingLinkRequest request =
                new CreatingLinkRequest("https://github.com/sc0116", "링크에 대한 간단한 소개입니다.");

        RestAssured.given(spec).log().all()
                .filter(document("reference-room/create",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        )))
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", javaStudyId)
                .body(request)
                .when().log().all()
                .post("/api/studies/{study-id}/reference-room/links")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
