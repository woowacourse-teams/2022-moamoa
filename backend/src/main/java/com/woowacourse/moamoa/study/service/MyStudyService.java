package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MyStudyService {

    public MyStudiesResponse getStudies(final Long id) {
        return new MyStudiesResponse(List.of());
    }
}
