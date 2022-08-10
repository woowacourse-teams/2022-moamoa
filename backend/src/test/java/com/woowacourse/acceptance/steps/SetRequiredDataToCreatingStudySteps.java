package com.woowacourse.acceptance.steps;

import com.woowacourse.moamoa.study.service.request.CreatingStudyRequestBuilder;
import java.time.LocalDate;

public class SetRequiredDataToCreatingStudySteps extends Steps {

    private final String token;
    private final CreatingStudyRequestBuilder builder;

    SetRequiredDataToCreatingStudySteps(final String token, final CreatingStudyRequestBuilder builder) {
        this.token = token;
        this.builder = builder;
    }

    public CreatingStudyStep 시작일자는(LocalDate date) {
        builder.startDate(date);
        return new CreatingStudyStep(token, builder);
    }
}
