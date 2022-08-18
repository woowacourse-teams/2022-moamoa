package com.woowacourse.acceptance.steps;

import com.woowacourse.moamoa.study.service.request.StudyRequestBuilder;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CreatingStudyStep extends Steps {

    private final String token;
    private final StudyRequestBuilder builder;

    CreatingStudyStep(final String token, final StudyRequestBuilder builder) {
        this.token = token;
        this.builder = builder;
    }

    public CreatingStudyStep 모집종료일자는(LocalDate date) {
        builder.enrollmentEndDate(date);
        return this;
    }

    public CreatingStudyStep 종료일자는(LocalDate date) {
        builder.endDate(date);
        return this;
    }

    public CreatingStudyStep 태그는(Long... tagIds) {
        builder.tagIds(List.of(tagIds));
        return this;
    }

    public CreatingStudyStep 모집인원은(int number) {
        builder.maxMemberCount(number);
        return this;
    }

    public long 생성한다() {
        try {
            final String location = RestAssured.given().log().all()
                    .cookie("accessToken", token)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(objectMapper.writeValueAsString(builder.build()))
                    .when().log().all()
                    .post("/api/studies")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract().header(HttpHeaders.LOCATION);
            return Long.parseLong(location.replaceAll("/api/studies/", ""));
        } catch (Exception e) {
            Assertions.fail("스터디 생성 실패");
            return -1;
        }
    }
}
