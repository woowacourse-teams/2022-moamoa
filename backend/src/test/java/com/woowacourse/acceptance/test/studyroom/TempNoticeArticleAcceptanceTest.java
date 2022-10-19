package com.woowacourse.acceptance.test.studyroom;

import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.베루스가;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.TempArticlesResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.TempArticleResponse;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("임시 공지글 관련 인수 테스트")
public class TempNoticeArticleAcceptanceTest extends AcceptanceTest {

    private LocalDate 지금;

    @BeforeEach
    void setUp() {
        지금 = LocalDate.now();
    }

    @DisplayName("임시글을 작성한다.")
    @Test
    void writeTempArticle() {
        // arrange
        final long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).생성한다();

        // act
        final long 게시글_ID = 그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목", "내용"));

        // assert
        final TempArticleResponse response = 그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().조회한다(게시글_ID);
        assertTempArticleResponse(response, "제목", "내용");
    }

    @DisplayName("임시글을 제거한다.")
    @Test
    void deleteTempArticle() {
        // arrange
        final long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        final long 게시글_ID = 그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목", "내용"));

        // act
        그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().삭제한다(게시글_ID);

        // assert
        그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().찾을_수_없다(게시글_ID);
    }

    @DisplayName("임시글을 수정한다.")
    @Test
    void updateTempArticle() {
        // arrange
        final long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        final long 게시글_ID = 그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목", "내용"));

        // act
        그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().수정한다(게시글_ID, new ArticleRequest("수정된 제목", "수정된 내용"));

        // assert
        final TempArticleResponse response = 그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().조회한다(게시글_ID);
        assertTempArticleResponse(response, "수정된 제목", "수정된 내용");
    }

    @DisplayName("작성한 임시글 목록을 조회한다.")
    @Test
    void getTempArticles() {
        // arrange
        final long 자바_스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        final long 리액트_스터디_ID = 그린론이().로그인하고().리액트_스터디를().시작일자는(지금).생성한다();

        그린론이().로그인하고().스터디에(자바_스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목1", "내용1"));
        그린론이().로그인하고().스터디에(자바_스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목2", "내용2"));
        그린론이().로그인하고().스터디에(자바_스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목3", "내용3"));
        final long 게시글4_ID = 그린론이().로그인하고().스터디에(자바_스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목4", "내용4"));
        final long 게시글5_ID = 그린론이().로그인하고().스터디에(자바_스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목5", "내용5"));
        final long 게시글6_ID = 그린론이().로그인하고().스터디에(리액트_스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목6", "내용6"));

        그린론이().로그인하고().스터디에(자바_스터디_ID).임시_게시글을().작성한다(new ArticleRequest("제목", "내용"));
        그린론이().로그인하고().스터디에(자바_스터디_ID).임시_게시글을().작성한다(new ArticleRequest("제목", "내용"));
        그린론이().로그인하고().스터디에(자바_스터디_ID).임시_게시글을().작성한다(new ArticleRequest("제목", "내용"));

        베루스가().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();
        베루스가().로그인하고().스터디에(자바_스터디_ID).임시_게시글을().작성한다(new ArticleRequest("제목", "내용"));
        베루스가().로그인하고().스터디에(자바_스터디_ID).임시_게시글을().작성한다(new ArticleRequest("제목", "내용"));
        베루스가().로그인하고().스터디에(자바_스터디_ID).임시_게시글을().작성한다(new ArticleRequest("제목", "내용"));

        // act
        final TempArticlesResponse responses = 그린론이().로그인하고().스터디에(자바_스터디_ID).임시_공지사항을().목록_조회한다(0, 3);

        // assert
        final Iterator<Long> expectedArticleIds = List.of(게시글6_ID, 게시글5_ID, 게시글4_ID).iterator();
        final Iterator<String> expectedTitles = List.of("제목6", "제목5", "제목4").iterator();
        final Iterator<String> expectedContents = List.of("내용6", "내용5", "내용4").iterator();
        final Iterator<Long> expectedStudyId = List.of(리액트_스터디_ID, 자바_스터디_ID, 자바_스터디_ID).iterator();

        for (TempArticleResponse response : responses) {
            assertTempArticleResponse(response,
                    expectedArticleIds.next(), expectedTitles.next(), expectedContents.next(), expectedStudyId.next()
            );
        }

        assertThat(responses.getCurrentPage()).isEqualTo(0);
        assertThat(responses.getLastPage()).isEqualTo(1);
        assertThat(responses.getTotalCount()).isEqualTo(6);
    }

    @DisplayName("임시글을 공개한다.")
    @Test
    void publishTempArticle() {
        // arrange
        final long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        final long 임시_게시글_ID = 그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목", "내용"));

        // act
        final long 게시글_ID = 그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().공개한다(임시_게시글_ID);

        // assert
        그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().찾을_수_없다(게시글_ID);

        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();
        final ArticleResponse response = 그린론이().로그인하고().스터디에(스터디_ID).공지사항을().조회한다(게시글_ID);
        assertThat(response.getTitle()).isEqualTo("제목");
        assertThat(response.getContent()).isEqualTo("내용");
        assertThat(response.getAuthor().getId()).isEqualTo(그린론_정보.getId());
    }

    private void assertTempArticleResponse(
            final TempArticleResponse response, final Long id, final String title, final String content, final Long studyId
    ){
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getStudy().getId()).isEqualTo(studyId);
        assertTempArticleResponse(response, title, content);
    }

    private void assertTempArticleResponse(
            final TempArticleResponse response, final String title, final String content
    ) {
        assertThat(response.getTitle()).isEqualTo(title);
        assertThat(response.getContent()).isEqualTo(content);
        assertThat(response.getCreatedDate()).isNotNull();
        assertThat(response.getLastModifiedDate()).isNotNull();
    }

}
