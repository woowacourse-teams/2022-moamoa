package com.woowacourse.moamoa.studyroom.query.data;

import lombok.Getter;

@Getter
public class StudyData {

    private final Long id;

    private final String title;

    public StudyData(final Long id, final String title) {
        this.id = id;
        this.title = title;
    }
}
