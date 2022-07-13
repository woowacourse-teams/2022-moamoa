package com.woowacourse.moamoa.tag.domain.repository;

import com.woowacourse.moamoa.tag.domain.Tag;
import java.util.List;

public interface TagRepository {

    List<Tag> findAll();
}
