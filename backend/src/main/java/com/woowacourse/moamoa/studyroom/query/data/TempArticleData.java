package com.woowacourse.moamoa.studyroom.query.data;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class TempArticleData {

    private final Long id;
    private final StudyData studyData;
    private final String title;
    private final String content;
    private final LocalDate createdDate;
    private final LocalDate lastModifiedDate;

    public TempArticleData(final Long id, final StudyData studyData,
                           final String title, final String content,
                           final LocalDate createdDate,
                           final LocalDate lastModifiedDate) {
        this.id = id;
        this.studyData = studyData;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
