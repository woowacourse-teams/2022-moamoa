package com.woowacourse.moamoa.referenceroom.service.response;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LinkResponse {

    private Long id;
    private AuthorResponse author;
    private String linkUrl;
    private String description;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
