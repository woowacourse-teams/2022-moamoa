package com.woowacourse.moamoa.studyroom.service.response.temp;

import com.woowacourse.moamoa.studyroom.query.data.TempArticleData;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TempArticleResponse {

    private String title;

    private String content;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    public TempArticleResponse(final TempArticleData tempArticleData) {
        this.title = tempArticleData.getTitle();
        this.content = tempArticleData.getContent();
        this.createdDate = tempArticleData.getCreatedDate();
        this.lastModifiedDate = tempArticleData.getLastModifiedDate();
    }
}
