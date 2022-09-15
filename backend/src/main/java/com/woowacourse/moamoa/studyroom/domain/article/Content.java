package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;

public interface Content<A extends Article<? extends Content<A>>> {

    A createArticle(StudyRoom studyRoom, Accessor accessor);
}
