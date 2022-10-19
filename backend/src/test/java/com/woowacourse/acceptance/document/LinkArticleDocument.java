package com.woowacourse.acceptance.document;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.restdocs.payload.JsonFieldType;

public class LinkArticleDocument extends Document {

    private LinkArticleDocument(final RequestSpecification spec) {
        super(spec);
    }

    public static LinkArticleDocument 링크_생성_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("reference-room/create",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("linkUrl").type(JsonFieldType.STRING).description("링크공유 url"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("링크공유 설명")
                        )
                )
        );
        return new LinkArticleDocument(documentSpec);
    }

    public static LinkArticleDocument 링크_삭제_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("reference-room/delete",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        )
                )
        );
        return new LinkArticleDocument(documentSpec);
    }

    public static LinkArticleDocument 링크_목록_조회_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("reference-room/list",
                        responseFields(
                                fieldWithPath("links[].id").type(JsonFieldType.NUMBER).description("링크공유 ID"),
                                fieldWithPath("links[].author.id").type(JsonFieldType.NUMBER)
                                        .description("링크공유 작성자 ID"),
                                fieldWithPath("links[].author.username").type(JsonFieldType.STRING)
                                        .description("링크공유 작성자 유저네임"),
                                fieldWithPath("links[].author.imageUrl").type(JsonFieldType.STRING)
                                        .description("링크공유 작성자 이미지 URL"),
                                fieldWithPath("links[].author.profileUrl").type(JsonFieldType.STRING)
                                        .description("링크공유 작성자 프로필 URL"),
                                fieldWithPath("links[].linkUrl").type(JsonFieldType.STRING).description("링크공유 URL"),
                                fieldWithPath("links[].description").type(JsonFieldType.STRING).description("링크공유 설명"),
                                fieldWithPath("links[].createdDate").type(JsonFieldType.STRING)
                                        .description("링크공유 생성일자"),
                                fieldWithPath("links[].lastModifiedDate").type(JsonFieldType.STRING)
                                        .description("링크공유 수정일자"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("데이터가 더 존재하는지 여부")
                        )
                )
        );
        return new LinkArticleDocument(documentSpec);
    }

    public static LinkArticleDocument 링크_수정_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("reference-room/update",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        )
                )
        );
        return new LinkArticleDocument(documentSpec);
    }
}
