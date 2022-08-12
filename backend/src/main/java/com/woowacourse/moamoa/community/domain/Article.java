package com.woowacourse.moamoa.community.domain;

public interface Article {

    void update(final String title, final String content);

    boolean isViewableBy(final Long studyId, final Long memberId);

    boolean isEditableBy(final Long studyId, final Long memberId);
}
