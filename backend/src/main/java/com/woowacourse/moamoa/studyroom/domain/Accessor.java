package com.woowacourse.moamoa.studyroom.domain;

public class Accessor {

    private final Long memberId;
    private final Long studyId;

    public Accessor(final Long memberId, final Long studyId) {
        this.memberId = memberId;
        this.studyId = studyId;
    }

    Long getStudyId() {
        return studyId;
    }

    Long getMemberId() {
        return memberId;
    }
}
