package com.woowacourse.moamoa.study.domain;

public enum StudyStatus {

    PREPARE, IN_PROGRESS, DONE;

    public StudyStatus nextStatus() {
        if (this.equals(PREPARE)) {
            return IN_PROGRESS;
        }
        if (this.equals(IN_PROGRESS)) {
            return DONE;
        }
        return PREPARE;
    }
}
