package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;

public enum ArticleType {

    NOTICE{
        @Override
        boolean isUnwritableAccessor(final StudyRoom studyRoom, final Accessor accessor) {
            return !studyRoom.isOwner(accessor);
        }

        @Override
        boolean isUneditableAccessor(final StudyRoom studyRoom, final Long authorId, final Accessor accessor) {
            return !studyRoom.isOwner(accessor);
        }
    },
    COMMUNITY{
        @Override
        boolean isUnwritableAccessor(final StudyRoom studyRoom, final Accessor accessor) {
            return !studyRoom.isPermittedAccessor(accessor);
        }

        @Override
        boolean isUneditableAccessor(final StudyRoom studyRoom, final Long authorId, final Accessor accessor) {
            return !(studyRoom.isPermittedAccessor(accessor) && authorId.equals(accessor.getMemberId()));
        }
    };

    abstract boolean isUnwritableAccessor(final StudyRoom studyRoom, final Accessor accessor);

    abstract boolean isUneditableAccessor(final StudyRoom studyRoom, final Long authorId, final Accessor accessor);
}
