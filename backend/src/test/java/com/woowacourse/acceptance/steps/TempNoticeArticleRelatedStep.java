package com.woowacourse.acceptance.steps;

import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.CreatedArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.TempArticlesResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.CreatedTempArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.TempArticleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class TempNoticeArticleRelatedStep extends Steps {

    private final String token;

    private final long studyId;

    public TempNoticeArticleRelatedStep(final String token, final long studyId) {
        this.token = token;
        this.studyId = studyId;
    }

    public Long 작성한다(final ArticleRequest request) {
        return spec.given().log().all()
                .header(AUTHORIZATION, token)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .pathParam("study-id", studyId)
                .when().log().all()
                .post("/api/studies/{study-id}/notice/draft-articles")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(CreatedTempArticleIdResponse.class)
                .getDraftArticleId();
    }

    public TempArticleResponse 조회한다(final long articleId) {
        return spec.given().log().all()
                .header(AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .pathParam("article-id", articleId)
                .when().log().all()
                .get("/api/studies/{study-id}/notice/draft-articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TempArticleResponse.class);
    }

    public void 찾을_수_없다(final long articleId) {
        spec.given().log().all()
                .header(AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .pathParam("article-id", articleId)
                .when().log().all()
                .get("/api/studies/{study-id}/notice/draft-articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    public void 삭제한다(final long articleId) {
        spec.given().log().all()
                .header(AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .pathParam("article-id", articleId)
                .when().log().all()
                .delete("/api/studies/{study-id}/notice/draft-articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public void 수정한다(final long articleId, final ArticleRequest articleRequest) {
        spec.given().log().all()
                .header(AUTHORIZATION, token)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", studyId)
                .pathParam("article-id", articleId)
                .body(articleRequest)
                .when().log().all()
                .put("/api/studies/{study-id}/notice/draft-articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public TempArticlesResponse 목록_조회한다(final int page, final int size) {
        return spec.given().log().all()
                .header(AUTHORIZATION, token)
                .param("page", page)
                .param("size", size)
                .when().log().all()
                .get("/api/draft/notice/articles")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TempArticlesResponse.class);
    }

    public Long 공개한다(final long articleId, final ArticleRequest request) {
        final CreatedArticleIdResponse response = spec.given().log().all()
                .header(AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .pathParam("article-id", articleId)
                .contentType(JSON)
                .body(request)
                .when().log().all()
                .post("/api/studies/{study-id}/notice/draft-articles/{article-id}/publish")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(CreatedArticleIdResponse.class);
        return response.getArticleId();
    }
}
