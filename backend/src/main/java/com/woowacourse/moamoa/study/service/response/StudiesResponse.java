package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudiesResponse {

    private List<StudySummaryData> studies;
    private boolean hasNext;
}
