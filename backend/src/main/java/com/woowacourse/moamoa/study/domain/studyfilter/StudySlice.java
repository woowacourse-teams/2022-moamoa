package com.woowacourse.moamoa.study.domain.studyfilter;

import com.woowacourse.moamoa.study.domain.study.Study;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudySlice {

    private List<Study> studies;
    private boolean hasNext;
}
