package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.study.query.data.MyStudyData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MyStudiesResponse {

    private final List<MyStudyData> studies;
}
