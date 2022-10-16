package com.woowacourse.moamoa.studyroom.service.response.temp;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class TempArticleResponse {

    private String title;

    private String content;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;
}
