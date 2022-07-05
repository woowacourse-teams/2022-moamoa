package com.woowacourse.moamoa.controller.dto;

import java.util.List;

public class StudiesResponse {

    private List<StudyResponse> studies;
    private boolean hasNext;

    public StudiesResponse(List<StudyResponse> studies, boolean hasNext) {
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
