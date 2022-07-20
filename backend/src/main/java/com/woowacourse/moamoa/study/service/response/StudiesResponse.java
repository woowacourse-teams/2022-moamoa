package com.woowacourse.moamoa.study.service.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudiesResponse {

    private List<StudyResponse> studies;
    private boolean hasNext;
}
