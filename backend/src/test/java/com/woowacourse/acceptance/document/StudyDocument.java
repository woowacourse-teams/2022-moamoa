package com.woowacourse.acceptance.document;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class StudyDocument extends Document {

    private StudyDocument(final RequestSpecification spec) {
        super(spec);
    }

    public static StudyDocument 스터디_참가_문서(RequestSpecification spec) {
        RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("studies/participant",
                        requestHeaders(
                                headerWithName("Authorization").description("JWT Token")
                        )
                )
        );

        return new StudyDocument(documentSpec);
    }
}
