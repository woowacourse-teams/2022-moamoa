package com.woowacourse.acceptance.steps;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
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
                .get("/api/studies/{study-id}/community/draft-articles/{article-id}")
                .then().log().all()
                .extract().as(TempArticleResponse.class);
    }
}
