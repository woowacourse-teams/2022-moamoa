package com.woowacourse.moamoa.studyroom.domain;

public class Accessor {

    private final Long memberId;
    private final Long studyId;

    public Accessor(final Long memberId, final Long studyId) {
        this.memberId = memberId;
        this.studyId = studyId;
    }

    public Long getStudyId() {
        return studyId;
    }

    public Long getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "Accessor{" +
                "memberId=" + memberId +
                ", studyId=" + studyId +
                '}';
    }
}
