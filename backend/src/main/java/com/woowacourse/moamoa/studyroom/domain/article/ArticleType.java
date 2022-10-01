package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;

public enum ArticleType {

    NOTICE{
        @Override
        boolean isWritableAccessor(final StudyRoom studyRoom, final Accessor accessor) {
            return studyRoom.isOwner(accessor);
        }

        @Override
        boolean isEditableAccessor(final StudyRoom studyRoom, final Accessor accessor) {
            throw new UnsupportedOperationException("#isEditableAccessor not implemented yet !!");
        }

        @Override
        Article createArticle(final StudyRoom studyRoom, final Accessor accessor, final Content content) {
            throw new UnsupportedOperationException("#isEditableAccessor not implemented yet !!");
        }
    },
    COMMUNITY{
        @Override
        boolean isWritableAccessor(final StudyRoom studyRoom, final Accessor accessor) {
            throw new UnsupportedOperationException("#isWritableAccessor not implemented yet !!");
        }

        @Override
        boolean isEditableAccessor(final StudyRoom studyRoom, final Accessor accessor) {
            throw new UnsupportedOperationException("#isEditableAccessor not implemented yet !!");
        }

        @Override
        Article createArticle(final StudyRoom studyRoom, final Accessor accessor, final Content content) {
            throw new UnsupportedOperationException("#createArticle not implemented yet !!");
        }
    };

    abstract boolean isWritableAccessor(final StudyRoom studyRoom, final Accessor accessor);

    abstract boolean isEditableAccessor(final StudyRoom studyRoom, final Accessor accessor);

    abstract Article createArticle(final StudyRoom studyRoom, final Accessor accessor, final Content content);
}
