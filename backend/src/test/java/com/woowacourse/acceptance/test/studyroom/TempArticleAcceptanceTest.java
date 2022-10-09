package com.woowacourse.acceptance.test.studyroom;

import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("임시글 관련 인수 테스트")
public class TempArticleAcceptanceTest extends AcceptanceTest {

    private LocalDate 지금;

    @BeforeEach
    void setUp() {
        지금 = LocalDate.now();
    }

    @Test
    void writeTempNotice() {
        final long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).생성한다();

//        RestAssured.given().log().all()
//                .post("/")
//                .when().log().all()
//                .then().log().all();
    }


}
