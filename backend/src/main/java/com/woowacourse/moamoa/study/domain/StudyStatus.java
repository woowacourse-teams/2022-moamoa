package com.woowacourse.moamoa.study.domain;

import java.util.Arrays;

public enum StudyStatus {

    PREPARE, IN_PROGRESS, DONE;

    public static StudyStatus find(String status) {
        return Arrays.stream(StudyStatus.values())
                .filter(studyStatus -> studyStatus.name().equals(status))
                .findAny()
                .get();
    }
}
