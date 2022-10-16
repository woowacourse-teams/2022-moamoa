package com.woowacourse.moamoa.studyroom.query.data;

import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class TempArticleData {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDate createdDate;
    private final LocalDate lastModifiedDate;

    public TempArticleData(final Long id, final String title, final String content,
                       final LocalDate createdDate,
                       final LocalDate lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
