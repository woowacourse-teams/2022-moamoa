package com.woowacourse.acceptance.document;

import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class ReviewDocument extends Document {

    private ReviewDocument(final RequestSpecification spec) {
        super(spec);
    }

    public static ReviewDocument 리뷰_생성_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("reviews/create")
        );
        return new ReviewDocument(documentSpec);
    }

    public static ReviewDocument 리뷰_전체_조회_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("reviews/list")
        );

        return new ReviewDocument(documentSpec);
    }

    public static ReviewDocument 리뷰_목록_조회_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("reviews/list-certain-number")
        );

        return new ReviewDocument(documentSpec);
    }

    public static ReviewDocument 리뷰_삭제_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("reviews/delete")
        );

        return new ReviewDocument(documentSpec);
    }

    public static ReviewDocument 리뷰_수정_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("reviews/update")
        );

        return new ReviewDocument(documentSpec);
    }
}
