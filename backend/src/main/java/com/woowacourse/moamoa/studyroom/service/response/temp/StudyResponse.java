package com.woowacourse.moamoa.studyroom.service.response.temp;

import com.woowacourse.moamoa.studyroom.query.data.StudyData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class StudyResponse {

    private long id;

    private String title;

    public StudyResponse(final StudyData data) {
        this.id = data.getId();
        this.title = data.getTitle();
    }
}
