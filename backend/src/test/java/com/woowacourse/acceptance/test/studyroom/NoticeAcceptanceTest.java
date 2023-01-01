package com.woowacourse.acceptance.test.studyroom;

import static com.woowacourse.acceptance.document.NoticeArticleDocument.공지사항_목록_조회_문서;
import static com.woowacourse.acceptance.document.NoticeArticleDocument.공지사항_삭제_문서;
import static com.woowacourse.acceptance.document.NoticeArticleDocument.공지사항_생성_문서;
import static com.woowacourse.acceptance.document.NoticeArticleDocument.공지사항_수정_문서;
import static com.woowacourse.acceptance.document.NoticeArticleDocument.공지사항_조회_문서;
import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.베루스가;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummaryResponse;
import com.woowacourse.moamoa.studyroom.service.response.AuthorResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NoticeAcceptanceTest extends AcceptanceTest {

    @DisplayName("스터디에 공지사항을 작성한다.")
    @Test
    void writeNotice() throws Exception {
        // arrange
        long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();

        // act
        Long articleId = 그린론이().로그인하고()
                .스터디에(스터디_ID)
                .공지사항을().API_문서화를_하고(공지사항_생성_문서(spec))
                .작성한다("공지사항 제목", "공지사항 내용");

        // assert
        final ArticleResponse actualResponse = 그린론이().로그인하고()
                .스터디에(스터디_ID)
                .공지사항을().API_문서화를_하고(공지사항_조회_문서(spec))
                .조회한다(articleId);

        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();
        final ArticleResponse expectedResponse = ArticleResponse.builder()
                .id(articleId)
                .author(new AuthorResponse(그린론_정보.getId(), 그린론_정보.getUsername(), 그린론_정보.getImageUrl(), 그린론_정보.getProfileUrl()))
                .title("공지사항 제목")
                .content("공지사항 내용")
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @DisplayName("스터디 공지사항 게시글을 삭제한다.")
    @Test
    void deleteCommunityArticle() {
        // arrange
        long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();
        long 공지사항_ID = 그린론이().로그인하고().스터디에(스터디_ID).공지사항을().작성한다("게시글 제목", "게시글 내용");
        String 토큰 = 그린론이().로그인한다();

        // act
        그린론이().로그인하고().스터디에(스터디_ID).공지사항을().API_문서화를_하고(공지사항_삭제_문서(spec)).삭제한다(공지사항_ID);

        // assert
        그린론이().로그인하고().스터디에(스터디_ID).공지사항을().찾을_수_없다(공지사항_ID);
    }

    @DisplayName("스터디 공지사항 전체 게시글을 조회한다.")
    @Test
    void getStudyCommunityArticles() {
        // arrange
        long 자바_스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();

        그린론이().로그인하고().스터디에(자바_스터디_ID).공지사항을().작성한다("자바 게시글 제목1", "자바 게시글 내용1");
        long 자바_공지글2_ID = 그린론이().로그인하고().스터디에(자바_스터디_ID).공지사항을().작성한다("자바 게시글 제목2", "자바 게시글 내용2");
        long 자바_공지글3_ID = 그린론이().로그인하고().스터디에(자바_스터디_ID).공지사항을().작성한다("자바 게시글 제목3", "자바 게시글 내용3");
        long 자바_공지글4_ID = 그린론이().로그인하고().스터디에(자바_스터디_ID).공지사항을().작성한다("자바 게시글 제목4", "자바 게시글 내용4");

        long 리액트_스터디_ID = 베루스가().로그인하고().리액트_스터디를().시작일자는(LocalDate.now()).생성한다();
        베루스가().로그인하고().스터디에(리액트_스터디_ID).공지사항을().작성한다("리액트 게시글 제목", "리액트 게시글 내용");

        // act
        final ArticleSummariesResponse response = 그린론이().로그인하고().스터디에(자바_스터디_ID)
                .공지사항을().API_문서화를_하고(공지사항_목록_조회_문서(spec)).목록_조회한다(0, 3);

        // assert
        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();
        AuthorResponse 그린론 = new AuthorResponse(그린론_정보.getId(), 그린론_정보.getUsername(), 그린론_정보.getImageUrl(), 그린론_정보.getProfileUrl());

        List<ArticleSummaryResponse> articles = List.of(
                new ArticleSummaryResponse(자바_공지글4_ID, 그린론, "자바 게시글 제목4", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(자바_공지글3_ID, 그린론, "자바 게시글 제목3", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(자바_공지글2_ID, 그린론, "자바 게시글 제목2", LocalDate.now(), LocalDate.now())
        );

        assertThat(response).isEqualTo(new ArticleSummariesResponse(articles, 0, 1, 4));
    }

    @DisplayName("스터디 커뮤니티 전체 공지사항을 기본 페이징 정보로 조회한다.")
    @Test
    void getStudyCommunityArticlesByDefaultPageable() {
        // arrange
        long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();
        long 공지글1_ID = 그린론이().로그인하고().스터디에(스터디_ID).공지사항을().작성한다("자바 게시글 제목1", "자바 게시글 내용1");
        long 공지글2_ID = 그린론이().로그인하고().스터디에(스터디_ID).공지사항을().작성한다("자바 게시글 제목2", "자바 게시글 내용2");
        long 공지글3_ID = 그린론이().로그인하고().스터디에(스터디_ID).공지사항을().작성한다("자바 게시글 제목3", "자바 게시글 내용3");
        long 공지글4_ID = 그린론이().로그인하고().스터디에(스터디_ID).공지사항을().작성한다("자바 게시글 제목4", "자바 게시글 내용4");

        // act
        final ArticleSummariesResponse response = 그린론이().로그인하고().스터디에(스터디_ID)
                .공지사항을().목록_조회한다();

        // assert
        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();
        AuthorResponse 그린론 = new AuthorResponse(그린론_정보.getId(), 그린론_정보.getUsername(), 그린론_정보.getImageUrl(), 그린론_정보.getProfileUrl());

        List<ArticleSummaryResponse> articles = List.of(
                new ArticleSummaryResponse(공지글4_ID, 그린론, "자바 게시글 제목4", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(공지글3_ID, 그린론, "자바 게시글 제목3", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(공지글2_ID, 그린론, "자바 게시글 제목2", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(공지글1_ID, 그린론, "자바 게시글 제목1", LocalDate.now(), LocalDate.now())
        );

        assertThat(response).isEqualTo(new ArticleSummariesResponse(articles, 0, 0, 4));
    }

    @DisplayName("공지사항을 수정한다.")
    @Test
    void updateArticleToCommunity() throws JsonProcessingException {
        // arrange
        long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();
        long 공지글_ID = 그린론이().로그인하고().스터디에(스터디_ID).공지사항을().작성한다("게시글 제목", "게시글 내용");

        // act
        final ArticleRequest request = new ArticleRequest("게시글 제목 수정", "게시글 내용 수정");
        그린론이().로그인하고().스터디에(스터디_ID).공지사항을().API_문서화를_하고(공지사항_수정_문서(spec)).수정한다(공지글_ID, request);

        // assert
        final ArticleResponse response = 그린론이().로그인하고().스터디에(스터디_ID).공지사항을().조회한다(공지글_ID);

        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();
        final AuthorResponse authorResponse = new AuthorResponse(그린론_정보.getId(), 그린론_정보.getUsername(), 그린론_정보.getImageUrl(), 그린론_정보.getProfileUrl());

        assertThat(response).isEqualTo(new ArticleResponse(공지글_ID, authorResponse, "게시글 제목 수정",
                "게시글 내용 수정", LocalDate.now(), LocalDate.now()));
    }
}
