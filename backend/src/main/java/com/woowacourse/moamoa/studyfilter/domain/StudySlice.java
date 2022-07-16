package com.woowacourse.moamoa.studyfilter.domain;

import com.woowacourse.moamoa.study.domain.Study;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudySlice {

    private List<Study> studies;
    private boolean hasNext;
}
