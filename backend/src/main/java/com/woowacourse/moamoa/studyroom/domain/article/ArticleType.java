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
        NoticeArticle createArticle(final StudyRoom studyRoom, final Accessor accessor, final NoticeContent content) {
            return new NoticeArticle(accessor.getMemberId(), studyRoom, content);
        }
    },
    COMMUNITY{
        @Override
        boolean isWritableAccessor(final StudyRoom studyRoom, final Accessor accessor) {
            throw new UnsupportedOperationException("#isWritableAccessor not implemented yet !!");
        }

        @Override
        NoticeArticle createArticle(final StudyRoom studyRoom, final Accessor accessor, final NoticeContent content) {
            throw new UnsupportedOperationException("#createArticle not implemented yet !!");
        }
    };

    abstract boolean isWritableAccessor(final StudyRoom studyRoom, final Accessor accessor);

    abstract NoticeArticle createArticle(final StudyRoom studyRoom, final Accessor accessor, final NoticeContent content);
}
