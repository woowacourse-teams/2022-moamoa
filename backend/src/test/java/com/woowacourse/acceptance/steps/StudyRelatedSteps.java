package com.woowacourse.acceptance.steps;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;

public class StudyRelatedSteps extends Steps {

    private final Long studyId;
    private final String token;

    StudyRelatedSteps(final Long studyId, final String token) {
        this.studyId = studyId;
        this.token = token;
    }

    public CommunityArticleRelatedSteps 게시글을() {
        return new CommunityArticleRelatedSteps(token, studyId);
    }

    public LinkArticleRelatedSteps 링크를() {
        return new LinkArticleRelatedSteps(token, studyId);
    }

    public NoticeArticleRelatedSteps 공지사항을() {
        return new NoticeArticleRelatedSteps(token, studyId);
    }

    public ReviewRelatedSteps 리뷰를() {
        return new ReviewRelatedSteps(token, studyId);
    }

    public TempNoticeArticleRelatedStep 임시_공지사항을() {
        return new TempNoticeArticleRelatedStep(token, studyId);
    }

    public TempCommunityArticleRelatedStep 임시_게시글을() {
        return new TempCommunityArticleRelatedStep(token, studyId);
    }

    public void 참여한다() {
        spec.log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .when().log().all()
                .post("/api/studies/{study-id}/members")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
