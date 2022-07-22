package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.study.domain.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudyResponse {

    private Long id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String status;

    public StudyResponse(final Study study) {
        this(study.getId(), study.getDetails().getTitle(), study.getDetails().getExcerpt(), study.getDetails().getThumbnail(),
                study.getDetails().getStatus());
    }
}
