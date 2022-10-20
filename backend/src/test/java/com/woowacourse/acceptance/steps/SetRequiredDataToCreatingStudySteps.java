package com.woowacourse.acceptance.steps;

import com.woowacourse.moamoa.study.service.request.StudyRequestBuilder;
import java.time.LocalDate;

public class SetRequiredDataToCreatingStudySteps {

    private final String token;
    private final StudyRequestBuilder builder;

    SetRequiredDataToCreatingStudySteps(final String token, final StudyRequestBuilder builder) {
        this.token = token;
        this.builder = builder;
    }

    public CreatingStudyStep 시작일자는(LocalDate date) {
        builder.startDate(date);
        return new CreatingStudyStep(token, builder);
    }
}
