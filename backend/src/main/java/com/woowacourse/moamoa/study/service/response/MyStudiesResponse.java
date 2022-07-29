package com.woowacourse.moamoa.study.service.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MyStudiesResponse {

    private final List<MyStudyResponse> studies;
}
