package com.woowacourse.acceptance.document;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

public class CommunityArticleDocument extends Document{

    private CommunityArticleDocument(final RequestSpecification spec) {
        super(spec);
    }

    public static CommunityArticleDocument 게시글_생성_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("article/create",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("생성된 게시글 url"),
                                headerWithName("Access-Control-Allow-Headers").description("접근 가능한 헤더")
                        )
                )
        );
        return new CommunityArticleDocument(documentSpec);
    }

    public static CommunityArticleDocument 게시글_조회_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("article/get",
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 ID"),
                                parameterWithName("article-id").description("게시글 식별 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 식별 ID"),
                                fieldWithPath("author").type(JsonFieldType.OBJECT).description("작성자"),
                                fieldWithPath("author.id").type(JsonFieldType.NUMBER)
                                        .description("작성자 github ID"),
                                fieldWithPath("author.username").type(JsonFieldType.STRING)
                                        .description("작성자 github 사용자 이름"),
                                fieldWithPath("author.imageUrl").type(JsonFieldType.STRING)
                                        .description("작성자 github 이미지 URL"),
                                fieldWithPath("author.profileUrl").type(JsonFieldType.STRING)
                                        .description("작성자 github 프로필 URL"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("게시글 작성일"),
                                fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING)
                                        .description("게시글 수정일")
                        )
                )
        );
        return new CommunityArticleDocument(documentSpec);
    }

    public static CommunityArticleDocument 게시글_삭제_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("article/delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 번호"),
                                parameterWithName("article-id").description("게시글 식별 번호")
                        )
                )
        );
        return new CommunityArticleDocument(documentSpec);
    }

    public static CommunityArticleDocument 게시글_목록_조회_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("article/list",
                        pathParameters(
                                parameterWithName("study-id").description("스터디 ID")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈")
                        ),
                        responseFields(
                                fieldWithPath("articles").type(JsonFieldType.ARRAY).description("게시물 목록"),
                                fieldWithPath("articles[].id").type(JsonFieldType.NUMBER).description("게시글 식별 ID"),
                                fieldWithPath("articles[].author").type(JsonFieldType.OBJECT).description("작성자"),
                                fieldWithPath("articles[].author.id").type(JsonFieldType.NUMBER)
                                        .description("작성자 github ID"),
                                fieldWithPath("articles[].author.username").type(JsonFieldType.STRING)
                                        .description("작성자 github 사용자 이름"),
                                fieldWithPath("articles[].author.imageUrl").type(JsonFieldType.STRING)
                                        .description("작성자 github 이미지 URL"),
                                fieldWithPath("articles[].author.profileUrl").type(JsonFieldType.STRING)
                                        .description("작성자 github 프로필 URL"),
                                fieldWithPath("articles[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("articles[].createdDate").type(JsonFieldType.STRING)
                                        .description("게시글 작성일"),
                                fieldWithPath("articles[].lastModifiedDate").type(JsonFieldType.STRING)
                                        .description("게시글 수정일"),
                                fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("lastPage").type(JsonFieldType.NUMBER).description("마지막 페이지 번호"),
                                fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("게시글 전체 갯수")
                        )
                )
        );
        return new CommunityArticleDocument(documentSpec);
    }

    public static CommunityArticleDocument 게시글_수정_문서(RequestSpecification spec) {
        final RequestSpecification documentSpec = RestAssured.given(spec).filter(
                document("article/update",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 번호"),
                                parameterWithName("article-id").description("게시글 식별 번호")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 수정 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용 수정")
                        )
                )
        );
        return new CommunityArticleDocument(documentSpec);
    }
}
