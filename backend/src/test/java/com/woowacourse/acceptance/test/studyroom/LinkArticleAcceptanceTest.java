package com.woowacourse.acceptance.test.studyroom;

import static com.woowacourse.acceptance.document.LinkArticleDocument.링크_목록_조회_문서;
import static com.woowacourse.acceptance.document.LinkArticleDocument.링크_삭제_문서;
import static com.woowacourse.acceptance.document.LinkArticleDocument.링크_생성_문서;
import static com.woowacourse.acceptance.document.LinkArticleDocument.링크_수정_문서;
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

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.AuthorResponse;
import com.woowacourse.moamoa.studyroom.service.response.LinkResponse;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("링크 모음 인수 테스트")
class LinkArticleAcceptanceTest extends AcceptanceTest {

    @DisplayName("참여한 스터디의 링크 공유실에 정상적으로 글을 작성한다.")
    @Test
    void shareLink() {
        // arrange
        final LocalDate 지금 = LocalDate.now();
        final Long javaStudyId = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();

        // act
        final LinkArticleRequest request =
                new LinkArticleRequest("https://github.com/sc0116", "링크에 대한 간단한 소개입니다.");
        final Long 링크_ID = 짱구가().로그인하고().스터디에(javaStudyId).링크를().API_문서화를_하고(링크_생성_문서(spec)).공유한다(request);

        // assert
        final LinksResponse response = 짱구가().로그인하고().스터디에(javaStudyId).링크를().목록_조회한다();
        final MemberResponse 짱구_정보 = 짱구가().로그인하고().정보를_가져온다();

        final AuthorResponse author = new AuthorResponse(
                짱구_정보.getId(), 짱구_정보.getUsername(), 짱구_정보.getImageUrl(), 짱구_정보.getProfileUrl()
        );
        assertThat(response.getLinks()).containsExactly(
                new LinkResponse(링크_ID, author, request.getLinkUrl(), request.getDescription(), 지금, 지금)
        );
    }

    @DisplayName("스터디에 전체 링크공유 목록을 조회할 수 있다.")
    @Test
    void getAllLink() {
        // arrange
        final LocalDate 지금 = LocalDate.now();
        final Long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();

        그린론이().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();
        디우가().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();
        베루스가().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();

        final LinkArticleRequest request1 = new LinkArticleRequest("https://github.com/sc0116", "짱구 링크.");
        final LinkArticleRequest request2 = new LinkArticleRequest("https://github.com/jaejae-yoo", "그린론 링크.");
        final LinkArticleRequest request3 = new LinkArticleRequest("https://github.com/tco0427", "디우 링크.");
        final LinkArticleRequest request4 = new LinkArticleRequest("https://github.com/wilgur513", "베루스 링크.");

        짱구가().로그인하고().스터디에(자바_스터디_ID).링크를().공유한다(request1);
        final Long 그린론_링크공유_ID = 그린론이().로그인하고().스터디에(자바_스터디_ID).링크를().공유한다(request2);
        final Long 디우_링크공유_ID = 디우가().로그인하고().스터디에(자바_스터디_ID).링크를().공유한다(request3);
        final Long 베루스_링크공유_ID = 베루스가().로그인하고().스터디에(자바_스터디_ID).링크를().공유한다(request4);
        final Long 짱구_링크공유_ID2 = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를().공유한다(request1);
        final Long 짱구_링크공유_ID3 = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를().공유한다(request1);

        // act
        final LinksResponse linksResponse = 짱구가().로그인하고()
                .스터디에(자바_스터디_ID)
                .링크를()
                .API_문서화를_하고(링크_목록_조회_문서(spec))
                .목록_조회한다(0, 5);

        // assert
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
        // arrange
        final LinkArticleRequest articleRequest = new LinkArticleRequest("https://github.com/sc0116",
                "링크 설명입니다.");
        final LocalDate 지금 = LocalDate.now();
        final Long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        final Long 짱구_링크공유_ID = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를().공유한다(articleRequest);
        final String token = 짱구가().로그인한다();

        // act
        final LinkArticleRequest editingLinkRequest = new LinkArticleRequest("https://github.com/woowacourse",
                "수정된 링크 설명입니다.");

        짱구가().로그인하고().스터디에(자바_스터디_ID)
                .링크를().API_문서화를_하고(링크_수정_문서(spec))
                .수정한다(짱구_링크공유_ID, editingLinkRequest);

        // assert
        final LinksResponse response = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를().목록_조회한다();

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
        // arrange
        final LinkArticleRequest articleRequest = new LinkArticleRequest("https://github.com/sc0116",
                "링크 설명입니다.");
        final LocalDate 지금 = LocalDate.now();
        final Long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        final Long 짱구_링크공유_ID = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를().공유한다(articleRequest);

        // act
        짱구가().로그인하고().스터디에(자바_스터디_ID).링크를().API_문서화를_하고(링크_삭제_문서(spec)).삭제한다(짱구_링크공유_ID);

        // assert
        final LinksResponse response = 짱구가().로그인하고().스터디에(자바_스터디_ID).링크를().목록_조회한다();
        assertThat(response.getLinks()).isEmpty();
        assertThat(response.isHasNext()).isFalse();
    }
}
