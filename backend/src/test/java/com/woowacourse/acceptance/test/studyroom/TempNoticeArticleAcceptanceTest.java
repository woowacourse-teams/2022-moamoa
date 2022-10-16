package com.woowacourse.acceptance.test.studyroom;

import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.temp.TempArticleResponse;
import java.time.LocalDate;
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

    @Test
    void writeTempNotice() {
        // arrange
        final long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).생성한다();

        // act
        final long 게시글_ID = 그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().작성한다(new ArticleRequest("제목", "내용"));

        // assert
        final TempArticleResponse response = 그린론이().로그인하고().스터디에(스터디_ID).임시_공지사항을().조회한다(게시글_ID);
        assertThat(response.getTitle()).isEqualTo("제목");
        assertThat(response.getContent()).isEqualTo("내용");
        assertThat(response.getCreatedDate()).isNotNull();
        assertThat(response.getLastModifiedDate()).isNotNull();
    }
}
