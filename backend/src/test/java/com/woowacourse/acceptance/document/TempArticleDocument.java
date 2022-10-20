package com.woowacourse.acceptance.document;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

public class TempArticleDocument extends Document {

    private TempArticleDocument(final RequestSpecification spec) {
        super(spec);
    }

    public static TempArticleDocument 임시글_저장_문서(final RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("temp-article/create",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 ID"),
                                parameterWithName("article-type").description("게시글 유형 ex) notice, community")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("임시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("임시글 내용")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("생성된 임시글 url")
                        )
                )
        );
        return new TempArticleDocument(documentSpec);
    }

    public static TempArticleDocument 임시글_수정_문서(final RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("temp-article/update",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 ID"),
                                parameterWithName("article-id").description("임시글 식별 ID"),
                                parameterWithName("article-type").description("게시글 유형 ex) notice, community")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("임시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("임시글 내용")
                        )
                )
        );
        return new TempArticleDocument(documentSpec);
    }

    public static TempArticleDocument 임시글_삭제_문서(final RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("temp-article/delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 번호"),
                                parameterWithName("article-id").description("게시글 식별 번호"),
                                parameterWithName("article-type").description("게시글 유형 ex) notice, community")
                        )
                )
        );
        return new TempArticleDocument(documentSpec);
    }

    public static TempArticleDocument 임시글_조회_문서(final RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("temp-article/get",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 ID"),
                                parameterWithName("article-id").description("공지사항 식별 ID"),
                                parameterWithName("article-type").description("게시글 유형 ex) notice, community")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("임시글 식별 ID"),
                                fieldWithPath("study.id").type(JsonFieldType.NUMBER).description("스터디 식별 ID"),
                                fieldWithPath("study.title").type(JsonFieldType.STRING).description("스터디 제목"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("임시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("임시글 내용"),
                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("임시글 작성일"),
                                fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("임시글 수정일")
                        )
                )
        );
        return new TempArticleDocument(documentSpec);
    }

    public static TempArticleDocument 임시글_목록_조회_문서(final RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("temp-articles/get",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("article-type").description("게시글 유형 ex) notice, community")
                        ),
                        responseFields(
                                fieldWithPath("draftArticles[]").type(JsonFieldType.ARRAY).description("임시글 목록"),
                                fieldWithPath("draftArticles[].id").type(JsonFieldType.NUMBER).description("임시글 식별 ID"),
                                fieldWithPath("draftArticles[].study.id").type(JsonFieldType.NUMBER).description("스터디 식별 ID"),
                                fieldWithPath("draftArticles[].study.title").type(JsonFieldType.STRING).description("스터디 제목"),
                                fieldWithPath("draftArticles[].title").type(JsonFieldType.STRING).description("임시글 제목"),
                                fieldWithPath("draftArticles[].content").type(JsonFieldType.STRING).description("임시글 내용"),
                                fieldWithPath("draftArticles[].createdDate").type(JsonFieldType.STRING).description("임시글 작성일"),
                                fieldWithPath("draftArticles[].lastModifiedDate").type(JsonFieldType.STRING).description("임시글 수정일"),
                                fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("lastPage").type(JsonFieldType.NUMBER).description("마지막 페이지"),
                                fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("목록 갯수")
                        )
                )
        );
        return new TempArticleDocument(documentSpec);
    }

    public static TempArticleDocument 임시글_공개_문서(final RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("temp-article/publish",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 ID"),
                                parameterWithName("article-id").description("공지사항 식별 ID"),
                                parameterWithName("article-type").description("게시글 유형 ex) notice, community")
                        )
                )
        );

        return new TempArticleDocument(documentSpec);
    }
}
