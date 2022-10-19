package com.woowacourse.acceptance.test.studyroom;

import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.베루스_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.베루스_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.베루스_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.짱구_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.짱구_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.짱구_프로필_URL;
import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static com.woowacourse.acceptance.steps.LoginSteps.베루스가;
import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.slack.api.model.Attachment;
import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.alarm.request.SlackMessageRequest;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.AuthorResponse;
import com.woowacourse.moamoa.studyroom.service.response.LinkResponse;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

@DisplayName("링크 모음 인수 테스트")
class LinkArticleAcceptanceTest extends AcceptanceTest {

    @DisplayName("참여한 스터디의 링크 공유실에 정상적으로 글을 작성한다.")
    @Test
    void shareLink() {
        final LocalDate 지금 = LocalDate.now();
        final Long javaStudyId = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();

        final String jwtToken = 짱구가().로그인한다();
        final LinkArticleRequest request =
                new LinkArticleRequest("https://github.com/sc0116", "링크에 대한 간단한 소개입니다.");

        RestAssured.given(spec).log().all()
                .filter(document("reference-room/create",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("linkUrl").type(JsonFieldType.STRING).description("링크공유 url"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("링크공유 설명")
                        )))
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", javaStudyId)
                .body(request)
                .when().log().all()
                .post("/api/studies/{study-id}/reference-room/links")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("스터디에 전체 링크공유 목록을 조회할 수 있다.")
    @Test
    void getAllLink() {
        final LocalDate 지금 = LocalDate.now();
        final Long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        그린론이().로그인한다();
        디우가().로그인한다();
        베루스가().로그인한다();

        final SlackMessageRequest slackMessageRequest = new SlackMessageRequest("jjanggu",
                List.of(Attachment.builder().title("📚 스터디에 새로운 크루가 참여했습니다.")
                        .text("<https://moamoa.space/my/study/|모아모아 바로가기>")
                        .color("#36288f").build()));

        그린론이().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();
        디우가().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();
        베루스가().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();

        final LinkArticleRequest request1 = new LinkArticleRequest("https://github.com/sc0116", "짱구 링크.");
        final LinkArticleRequest request2 = new LinkArticleRequest("https://github.com/jaejae-yoo", "그린론 링크.");
        final LinkArticleRequest request3 = new LinkArticleRequest("https://github.com/tco0427", "디우 링크.");
        final LinkArticleRequest request4 = new LinkArticleRequest("https://github.com/wilgur513", "베루스 링크.");

        final Long 짱구_링크공유_ID = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를_공유한다(request1);
        final Long 그린론_링크공유_ID = 그린론이().로그인하고().스터디에(자바_스터디_ID).링크를_공유한다(request2);
        final Long 디우_링크공유_ID = 디우가().로그인하고().스터디에(자바_스터디_ID).링크를_공유한다(request3);
        final Long 베루스_링크공유_ID = 베루스가().로그인하고().스터디에(자바_스터디_ID).링크를_공유한다(request4);
        final Long 짱구_링크공유_ID2 = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를_공유한다(request1);
        final Long 짱구_링크공유_ID3 = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를_공유한다(request1);

        final LinksResponse linksResponse = RestAssured.given(spec).log().all()
                .filter(document("reference-room/list",
                        responseFields(
                                fieldWithPath("links[].id").type(JsonFieldType.NUMBER).description("링크공유 ID"),
                                fieldWithPath("links[].author.id").type(JsonFieldType.NUMBER).description("링크공유 작성자 ID"),
                                fieldWithPath("links[].author.username").type(JsonFieldType.STRING).description("링크공유 작성자 유저네임"),
                                fieldWithPath("links[].author.imageUrl").type(JsonFieldType.STRING).description("링크공유 작성자 이미지 URL"),
                                fieldWithPath("links[].author.profileUrl").type(JsonFieldType.STRING).description("링크공유 작성자 프로필 URL"),
                                fieldWithPath("links[].linkUrl").type(JsonFieldType.STRING).description("링크공유 URL"),
                                fieldWithPath("links[].description").type(JsonFieldType.STRING).description("링크공유 설명"),
                                fieldWithPath("links[].createdDate").type(JsonFieldType.STRING).description("링크공유 생성일자"),
                                fieldWithPath("links[].lastModifiedDate").type(JsonFieldType.STRING).description("링크공유 수정일자"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("데이터가 더 존재하는지 여부")
                        )))
                .pathParam("study-id", 자바_스터디_ID)
                .param("page", 0)
                .param("size", 5)
                .when().log().all()
                .get("/api/studies/{study-id}/reference-room/links")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(LinksResponse.class);

        final LocalDate 리뷰_생성일 = LocalDate.now();
        final LocalDate 리뷰_수정일 = LocalDate.now();

        final MemberResponse 짱구_정보 = 짱구가().로그인하고().정보를_가져온다();
        final MemberResponse 디우_정보 = 디우가().로그인하고().정보를_가져온다();
        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();
        final MemberResponse 베루스_정보 = 베루스가().로그인하고().정보를_가져온다();

        final AuthorResponse 그린론 = new AuthorResponse(그린론_정보.getId(), 그린론_이름, 그린론_이미지_URL, 그린론_프로필_URL);
        final LinkResponse 그린론_응답
                = new LinkResponse(그린론_링크공유_ID, 그린론, request2.getLinkUrl(), request2.getDescription(), 리뷰_생성일, 리뷰_수정일);

        final AuthorResponse 디우 = new AuthorResponse(디우_정보.getId(), 디우_이름, 디우_이미지_URL, 디우_프로필_URL);
        final LinkResponse 디우_응답
                = new LinkResponse(디우_링크공유_ID, 디우, request3.getLinkUrl(), request3.getDescription(), 리뷰_생성일, 리뷰_수정일);

        final AuthorResponse 베루스 = new AuthorResponse(베루스_정보.getId(), 베루스_이름, 베루스_이미지_URL, 베루스_프로필_URL);
        final LinkResponse 베루스_응답
                = new LinkResponse(베루스_링크공유_ID, 베루스, request4.getLinkUrl(), request4.getDescription(), 리뷰_생성일, 리뷰_수정일);

        final AuthorResponse 짱구 = new AuthorResponse(짱구_정보.getId(), 짱구_이름, 짱구_이미지_URL, 짱구_프로필_URL);
        final LinkResponse 짱구_응답2
                = new LinkResponse(짱구_링크공유_ID2, 짱구, request1.getLinkUrl(), request1.getDescription(), 리뷰_생성일, 리뷰_수정일);

        final LinkResponse 짱구_응답3
                = new LinkResponse(짱구_링크공유_ID3, 짱구, request1.getLinkUrl(), request1.getDescription(), 리뷰_생성일, 리뷰_수정일);

        assertAll(
                () -> assertThat(linksResponse.isHasNext()).isTrue(),
                () -> assertThat(linksResponse.getLinks())
                        .containsExactly(짱구_응답3, 짱구_응답2, 베루스_응답, 디우_응답, 그린론_응답)
        );
    }

    @DisplayName("작성한 링크 공유글을 수정할 수 있다.")
    @Test
    void updateLink() {
        final LinkArticleRequest articleRequest = new LinkArticleRequest("https://github.com/sc0116",
                "링크 설명입니다.");
        final LocalDate 지금 = LocalDate.now();
        final Long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        final Long 짱구_링크공유_ID = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를_공유한다(articleRequest);
        final String token = 짱구가().로그인한다();

        final LinkArticleRequest editingLinkRequest = new LinkArticleRequest("https://github.com/woowacourse",
                "수정된 링크 설명입니다.");

        RestAssured.given(spec).log().all()
                .filter(document("reference-room/update",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        )))
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", 자바_스터디_ID)
                .pathParam("link-id", 짱구_링크공유_ID)
                .body(editingLinkRequest)
                .when().log().all()
                .put("/api/studies/{study-id}/reference-room/links/{link-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());

        final LinksResponse response = RestAssured.given().log().all()
                .pathParam("study-id", 자바_스터디_ID)
                .when().log().all()
                .get("/api/studies/{study-id}/reference-room/links")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(LinksResponse.class);

        final LocalDate 링크_생성일 = 지금;
        final LocalDate 링크_수정일 = 지금;

        final MemberResponse 짱구_정보 = 짱구가().로그인하고().정보를_가져온다();
        final AuthorResponse 짱구 = new AuthorResponse(짱구_정보.getId(), 짱구_이름, 짱구_이미지_URL, 짱구_프로필_URL);
        final LinkResponse 짱구_링크 = new LinkResponse(짱구_링크공유_ID, 짱구, editingLinkRequest.getLinkUrl(),
                editingLinkRequest.getDescription(), 링크_생성일, 링크_수정일);

        assertThat(response.getLinks()).containsExactlyInAnyOrder(짱구_링크);
        assertThat(response.isHasNext()).isFalse();
    }

    @DisplayName("작성한 링크 공유글을 삭제할 수 있다.")
    @Test
    void deleteLink() {
        final LinkArticleRequest articleRequest = new LinkArticleRequest("https://github.com/sc0116",
                "링크 설명입니다.");
        final LocalDate 지금 = LocalDate.now();
        final Long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        final Long 짱구_링크공유_ID = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를_공유한다(articleRequest);
        final String token = 짱구가().로그인한다();

        RestAssured.given(spec).log().all()
                .filter(document("reference-room/delete",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer Token")
                        )))
                .header(HttpHeaders.AUTHORIZATION, token)
                .pathParam("study-id", 자바_스터디_ID)
                .pathParam("link-id", 짱구_링크공유_ID)
                .when().log().all()
                .delete("/api/studies/{study-id}/reference-room/links/{link-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());

        final LinksResponse response = RestAssured.given().log().all()
                .pathParam("study-id", 자바_스터디_ID)
                .when().log().all()
                .get("/api/studies/{study-id}/reference-room/links")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(LinksResponse.class);

        assertThat(response.getLinks()).isEmpty();
        assertThat(response.isHasNext()).isFalse();
    }
}
