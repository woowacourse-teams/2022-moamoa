package com.woowacourse.acceptance.steps;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacourse.acceptance.document.LinkArticleDocument;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class LinkArticleRelatedSteps extends Steps<LinkArticleRelatedSteps, LinkArticleDocument> {

    private final String token;
    private final Long studyId;

    LinkArticleRelatedSteps(final String token, final Long studyId) {
        this.token = token;
        this.studyId = studyId;
    }

    public Long 공유한다(final LinkArticleRequest request) {
        try {
            final String location = spec.log().all()
                    .header(AUTHORIZATION, token)
                    .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .pathParams("study-id", studyId)
                    .body(objectMapper.writeValueAsString(request))
                    .when().post("/api/studies/{study-id}/reference-room/links")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract().header(HttpHeaders.LOCATION);
            return Long.parseLong(location.replaceAll("/api/studies/" + studyId + "/reference-room/links/", ""));
        } catch (Exception e) {
            Assertions.fail("링크 공유 작성 실패");
            return null;
        }
    }

    public LinksResponse 목록_조회한다() {
        return spec.log().all()
                .pathParam("study-id", studyId)
                .when().log().all()
                .get("/api/studies/{study-id}/reference-room/links")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(LinksResponse.class);
    }

    public LinksResponse 목록_조회한다(final int page, final int size) {
        return spec.log().all()
                .pathParam("study-id", studyId)
                .param("page", page)
                .param("size", size)
                .when().log().all()
                .get("/api/studies/{study-id}/reference-room/links")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(LinksResponse.class);
    }

    public void 수정한다(final Long linkId, final LinkArticleRequest request) {
        spec.log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", studyId)
                .pathParam("link-id", linkId)
                .body(request)
                .when().log().all()
                .put("/api/studies/{study-id}/reference-room/links/{link-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    public void 삭제한다(final Long linkId) {
        spec.log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .pathParam("link-id", linkId)
                .when().log().all()
                .delete("/api/studies/{study-id}/reference-room/links/{link-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }
}
