package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.studyroom.domain.Accessor;

public interface Article<T extends Content<? extends Article<T>>> {

    void update(final Accessor accessor, final T content);

    void delete(final Accessor accessor);
}
