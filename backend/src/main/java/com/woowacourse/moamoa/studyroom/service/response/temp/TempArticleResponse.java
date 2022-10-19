package com.woowacourse.moamoa.studyroom.service.response.temp;

import com.woowacourse.moamoa.studyroom.query.data.TempArticleData;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TempArticleResponse {

    private Long id;

    private String title;

    private String content;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    private StudyResponse study;

    public TempArticleResponse(final TempArticleData tempArticleData) {
        this.id = tempArticleData.getId();
        this.title = tempArticleData.getTitle();
        this.content = tempArticleData.getContent();
        this.createdDate = tempArticleData.getCreatedDate();
        this.lastModifiedDate = tempArticleData.getLastModifiedDate();
        this.study = new StudyResponse(tempArticleData.getStudyData());
    }
}
