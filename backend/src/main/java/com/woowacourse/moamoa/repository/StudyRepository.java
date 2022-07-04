package com.woowacourse.moamoa.repository;

import com.woowacourse.moamoa.domain.Study;
import java.util.List;

public interface StudyRepository {

    List<Study> findAll(int page, int size);
}
