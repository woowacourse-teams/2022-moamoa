package com.woowacourse.acceptance.steps;

import static com.woowacourse.acceptance.fixture.StudyFixtures.HTTP_스터디_설명;
import static com.woowacourse.acceptance.fixture.StudyFixtures.HTTP_스터디_썸네일;
import static com.woowacourse.acceptance.fixture.StudyFixtures.HTTP_스터디_요약;
import static com.woowacourse.acceptance.fixture.StudyFixtures.HTTP_스터디_제목;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리눅스_스터디_설명;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리눅스_스터디_썸네일;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리눅스_스터디_요약;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리눅스_스터디_제목;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리액트_스터디_설명;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리액트_스터디_썸네일;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리액트_스터디_요약;
import static com.woowacourse.acceptance.fixture.StudyFixtures.리액트_스터디_제목;
import static com.woowacourse.acceptance.fixture.StudyFixtures.알고리즘_스터디_설명;
import static com.woowacourse.acceptance.fixture.StudyFixtures.알고리즘_스터디_썸네일;
import static com.woowacourse.acceptance.fixture.StudyFixtures.알고리즘_스터디_요약;
import static com.woowacourse.acceptance.fixture.StudyFixtures.알고리즘_스터디_제목;
import static com.woowacourse.acceptance.fixture.StudyFixtures.자바_스터디_설명;
import static com.woowacourse.acceptance.fixture.StudyFixtures.자바_스터디_썸네일;
import static com.woowacourse.acceptance.fixture.StudyFixtures.자바_스터디_요약;
import static com.woowacourse.acceptance.fixture.StudyFixtures.자바_스터디_제목;
import static com.woowacourse.acceptance.fixture.StudyFixtures.자바스크립트_스터디_설명;
import static com.woowacourse.acceptance.fixture.StudyFixtures.자바스크립트_스터디_썸네일;
import static com.woowacourse.acceptance.fixture.StudyFixtures.자바스크립트_스터디_요약;
import static com.woowacourse.acceptance.fixture.StudyFixtures.자바스크립트_스터디_제목;

import com.woowacourse.moamoa.study.service.request.CreatingStudyRequestBuilder;

public class AfterLoginSteps extends Steps {

    private final String token;

    AfterLoginSteps(final String token) {
        this.token = token;
    }

    public SetRequiredDataToCreatingStudySteps 자바_스터디를() {
        CreatingStudyRequestBuilder builder = new CreatingStudyRequestBuilder()
                .title(자바_스터디_제목).excerpt(자바_스터디_요약).description(자바_스터디_설명).thumbnail(자바_스터디_썸네일);

        return new SetRequiredDataToCreatingStudySteps(token, builder);
    }

    public SetRequiredDataToCreatingStudySteps 리액트_스터디를() {
        CreatingStudyRequestBuilder builder = new CreatingStudyRequestBuilder()
                .title(리액트_스터디_제목).excerpt(리액트_스터디_요약).description(리액트_스터디_설명).thumbnail(리액트_스터디_썸네일);

        return new SetRequiredDataToCreatingStudySteps(token, builder);
    }

    public SetRequiredDataToCreatingStudySteps 자바스크립트_스터디를() {
        CreatingStudyRequestBuilder builder = new CreatingStudyRequestBuilder()
                .title(자바스크립트_스터디_제목).excerpt(자바스크립트_스터디_요약)
                .description(자바스크립트_스터디_설명).thumbnail(자바스크립트_스터디_썸네일);

        return new SetRequiredDataToCreatingStudySteps(token, builder);
    }

    public SetRequiredDataToCreatingStudySteps HTTP_스터디를() {
        CreatingStudyRequestBuilder builder = new CreatingStudyRequestBuilder()
                .title(HTTP_스터디_제목).excerpt(HTTP_스터디_요약)
                .description(HTTP_스터디_설명).thumbnail(HTTP_스터디_썸네일);

        return new SetRequiredDataToCreatingStudySteps(token, builder);
    }

    public SetRequiredDataToCreatingStudySteps 알고리즘_스터디를() {
        CreatingStudyRequestBuilder builder = new CreatingStudyRequestBuilder()
                .title(알고리즘_스터디_제목).excerpt(알고리즘_스터디_요약)
                .description(알고리즘_스터디_설명).thumbnail(알고리즘_스터디_썸네일);

        return new SetRequiredDataToCreatingStudySteps(token, builder);
    }

    public SetRequiredDataToCreatingStudySteps 리눅스_스터디를() {
        CreatingStudyRequestBuilder builder = new CreatingStudyRequestBuilder()
                .title(리눅스_스터디_제목).excerpt(리눅스_스터디_요약)
                .description(리눅스_스터디_설명).thumbnail(리눅스_스터디_썸네일);

        return new SetRequiredDataToCreatingStudySteps(token, builder);
    }

    public StudyRelatedSteps 스터디에(final Long studyId) {
        return new StudyRelatedSteps(studyId, token);
    }
}
