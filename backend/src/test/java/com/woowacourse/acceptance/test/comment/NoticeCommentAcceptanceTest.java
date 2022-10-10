package com.woowacourse.acceptance.test.comment;

import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static com.woowacourse.acceptance.steps.LoginSteps.베루스가;
import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.NOTICE;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.comment.service.request.CommentRequest;
import com.woowacourse.moamoa.comment.service.response.CommentResponse;
import com.woowacourse.moamoa.comment.service.response.CommentsResponse;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("공지사항 댓글 인수 테스트")
class NoticeCommentAcceptanceTest extends AcceptanceTest {

    @DisplayName("공지사항에 댓글 내용과 함께 댓글을 작성할 수 있다.")
    @Test
    void writeCommentAtNotice() {
        // given
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디 = 디우가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        베루스가().로그인하고().스터디에(자바_스터디).참여한다();
        long 자바_공지사항 = 디우가().로그인하고().스터디에(자바_스터디).공지사항을_작성한다("자바 공지사항", "자바 공지사항 내용");
        final String token = 디우가().로그인한다();
        final MemberResponse 디우 = 디우가().로그인하고().정보를_가져온다();

        final CommentRequest commentRequest = new CommentRequest("댓글 내용");

        // when
        final String location = RestAssured.given(spec).log().all()
                .header(AUTHORIZATION, token)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .pathParam("study-id", 자바_스터디)
                .pathParam("article-type", "notice")
                .pathParam("article-id", 자바_공지사항)
                .body(commentRequest)
                .filter(document("comments/notice/create",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("액세스 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 ID"),
                                parameterWithName("article-type").description("게시글 타입"),
                                parameterWithName("article-id").description("게시글 ID")
                        )))
                .when().log().all()
                .post("/api/studies/{study-id}/{article-type}/{article-id}/comments")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header(LOCATION);

        // then
        final long commentId = Long.parseLong(
                location.replaceAll("/api/studies/\\d++/notice/\\d++/comments/", ""));
        final CommentsResponse response = RestAssured.given(spec).log().all()
                .pathParam("study-id", 자바_스터디)
                .pathParam("article-id", 자바_공지사항)
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when().log().all()
                .get("/api/studies/{study-id}/notice/{article-id}/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CommentsResponse.class);

        final List<CommentResponse> comments = response.getComments();
        assertThat(comments).hasSize(1)
                .extracting("id", "author.memberId", "content")
                .contains(tuple(commentId, 디우.getId(), "댓글 내용"));
    }

    @DisplayName("공지사항의 댓글 목록을 전체 조회할 수 있다.")
    @Test
    void getCommentsFromNotice() {
        // given
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디 = 디우가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        long 자바_공지사항 = 디우가().로그인하고().스터디에(자바_스터디).공지사항을_작성한다("자바 공지사항", "자바 공지사항 내용");
        디우가().로그인하고().스터디에(자바_스터디).댓글을_작성한다(NOTICE, 자바_공지사항, "댓글 내용1");
        final Long 댓글_내용2_ID = 디우가().로그인하고().스터디에(자바_스터디).댓글을_작성한다(NOTICE, 자바_공지사항, "댓글 내용2");
        final Long 댓글_내용3_ID = 디우가().로그인하고().스터디에(자바_스터디).댓글을_작성한다(NOTICE, 자바_공지사항, "댓글 내용3");
        final MemberResponse 디우 = 디우가().로그인하고().정보를_가져온다();

        // when
        final CommentsResponse response = RestAssured.given(spec).log().all()
                .pathParam("study-id", 자바_스터디)
                .pathParam("article-id", 자바_공지사항)
                .pathParam("article-type", "notice")
                .queryParam("page", 0)
                .queryParam("size", 2)
                .filter(document("comments/notice/list",
                        pathParameters(
                                parameterWithName("study-id").description("스터디 ID"),
                                parameterWithName("article-type").description("게시글 타입"),
                                parameterWithName("article-id").description("게시글 ID")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈")
                        )))
                .when().log().all()
                .get("/api/studies/{study-id}/{article-type}/{article-id}/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CommentsResponse.class);

        // then
        final List<CommentResponse> comments = response.getComments();
        assertThat(comments).hasSize(2)
                .extracting("id", "author.memberId", "content")
                .containsExactly(
                        tuple(댓글_내용3_ID, 디우.getId(), "댓글 내용3"),
                        tuple(댓글_내용2_ID, 디우.getId(), "댓글 내용2")
                );
    }

    @DisplayName("공지사항에 본인이 작성한 댓글을 수정할 수 있다.")
    @Test
    void updateNoticeComment() {
        // given
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디 = 디우가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        long 자바_공지사항 = 디우가().로그인하고().스터디에(자바_스터디).공지사항을_작성한다("자바 공지사항", "자바 공지사항 내용");
        final Long 댓글_ID = 디우가().로그인하고().스터디에(자바_스터디).댓글을_작성한다(NOTICE, 자바_공지사항, "댓글 내용");
        final String token = 디우가().로그인한다();
        final MemberResponse 디우 = 디우가().로그인하고().정보를_가져온다();

        final CommentRequest request = new CommentRequest("수정된 댓글 내용");

        // when
        RestAssured.given(spec).log().all()
                .header(AUTHORIZATION, token)
                .pathParam("study-id", 자바_스터디)
                .pathParam("article-type", "notice")
                .pathParam("article-id", 자바_공지사항)
                .pathParam("comment-id", 댓글_ID)
                .contentType(APPLICATION_JSON_VALUE)
                .body(request)
                .filter(document("comments/notice/update",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("액세스 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 ID"),
                                parameterWithName("article-type").description("게시글 타입"),
                                parameterWithName("article-id").description("게시글 ID"),
                                parameterWithName("comment-id").description("댓글 ID")
                        ),
                        requestFields(
                                fieldWithPath("content").type(STRING).description("댓글 내용")
                        )
                ))
                .when().log().all()
                .put("/api/studies/{study-id}/{article-type}/{article-id}/comments/{comment-id}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // then
        final CommentsResponse response = RestAssured.given(spec).log().all()
                .pathParam("study-id", 자바_스터디)
                .pathParam("article-type", "notice")
                .pathParam("article-id", 자바_공지사항)
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when().log().all()
                .get("/api/studies/{study-id}/{article-type}/{article-id}/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CommentsResponse.class);

        final List<CommentResponse> comments = response.getComments();
        assertThat(comments).hasSize(1)
                .extracting("id", "author.memberId", "content")
                .contains(tuple(댓글_ID, 디우.getId(), "수정된 댓글 내용"));
    }

    @DisplayName("공지사항에 본인이 작성한 댓글을 삭제할 수 있다.")
    @Test
    void deleteNoticeComment() {
        // given
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디 = 디우가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        long 자바_공지사항 = 디우가().로그인하고().스터디에(자바_스터디).공지사항을_작성한다("자바 공지사항", "자바 공지사항 내용");

        final Long 댓글_ID = 디우가().로그인하고().스터디에(자바_스터디).댓글을_작성한다(NOTICE, 자바_공지사항, "댓글 내용");
        final String token = 디우가().로그인한다();

        // when
        RestAssured.given(spec).log().all()
                .header(AUTHORIZATION, token)
                .pathParam("study-id", 자바_스터디)
                .pathParam("article-type", "notice")
                .pathParam("article-id", 자바_공지사항)
                .pathParam("comment-id", 댓글_ID)
                .filter(document("comments/notice/delete",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("액세스 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 ID"),
                                parameterWithName("article-type").description("게시글 타입"),
                                parameterWithName("article-id").description("게시글 ID"),
                                parameterWithName("comment-id").description("댓글 ID")
                        )
                ))
                .when().log().all()
                .delete("/api/studies/{study-id}/{article-type}}/{article-id}/comments/{comment-id}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // then
        final CommentsResponse response = RestAssured.given(spec).log().all()
                .pathParam("study-id", 자바_스터디)
                .pathParam("article-type", "notice")
                .pathParam("article-id", 자바_공지사항)
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when().log().all()
                .get("/api/studies/{study-id}/{article-type}/{article-id}/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CommentsResponse.class);

        final List<CommentResponse> comments = response.getComments();
        assertThat(comments).isEmpty();
    }
}
