package com.woowacourse.acceptance.test.referenceroom;

import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.referenceroom.service.request.CreatingLinkRequest;
import com.woowacourse.moamoa.referenceroom.service.request.EditingLinkRequest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("링크 모음 인수 테스트")
public class ReferenceRoomAcceptanceTest extends AcceptanceTest {


    @DisplayName("참여한 스터디의 링크 공유실에 정상적으로 글을 작성한다.")
    @Test
    void shareLink() {
        final LocalDate 지금 = LocalDate.now();
        final Long javaStudyId = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();

        final String jwtToken = 짱구가().로그인한다();
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

    @DisplayName("작성한 링크 공유글을 수정할 수 있다.")
    @Test
    void updateLink() {
        final CreatingLinkRequest creatingLinkRequest = new CreatingLinkRequest("https://github.com/sc0116",
                "링크 설명입니다.");
        final LocalDate 지금 = LocalDate.now();
        final Long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        final Long 짱구_링크공유_ID = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를_공유한다(creatingLinkRequest);
        final String token = 짱구가().로그인한다();

        final EditingLinkRequest editingLinkRequest = new EditingLinkRequest("https://github.com/woowacourse",
                "수정된 링크 설명입니다.");

        RestAssured.given(spec).log().all()
                .filter(document("reference-room/update",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        )))
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", 자바_스터디_ID)
                .pathParam("link-id", 짱구_링크공유_ID)
                .body(editingLinkRequest)
                .when().log().all()
                .put("/api/studies/{study-id}/reference-room/links/{link-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("작성한 링크 공유글을 삭제할 수 있다.")
    @Test
    void deleteLink() {
        final CreatingLinkRequest creatingLinkRequest = new CreatingLinkRequest("https://github.com/sc0116",
                "링크 설명입니다.");
        final LocalDate 지금 = LocalDate.now();
        final Long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        final Long 짱구_링크공유_ID = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를_공유한다(creatingLinkRequest);
        final String token = 짱구가().로그인한다();

        RestAssured.given(spec).log().all()
                .filter(document("reference-room/delete",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        )))
                .header(HttpHeaders.AUTHORIZATION, token)
                .pathParam("study-id", 자바_스터디_ID)
                .pathParam("link-id", 짱구_링크공유_ID)
                .when().log().all()
                .delete("/api/studies/{study-id}/reference-room/links/{link-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }
}
