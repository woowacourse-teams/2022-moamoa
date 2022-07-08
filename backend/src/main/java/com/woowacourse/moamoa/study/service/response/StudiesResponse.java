package com.woowacourse.moamoa.study.service.response;

import java.util.List;

public class StudiesResponse {

    private List<StudyResponse> studies;
    private boolean hasNext;

    public StudiesResponse(final List<StudyResponse> studies, final boolean hasNext) {
        this.studies = studies;
        this.hasNext = hasNext;
    }

    public StudiesResponse() {
    }

    public List<StudyResponse> getStudies() {
        return studies;
    }

    public boolean isHasNext() {
        return hasNext;
    }
}
