package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.common.exception.NotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import java.util.Arrays;

public enum ArticleType {

    NOTICE("notice") {
        @Override
        boolean isUnwritableAccessor(final StudyRoom studyRoom, final Accessor accessor) {
            return !studyRoom.isOwner(accessor);
        }

        @Override
        boolean isUneditableAccessor(final StudyRoom studyRoom, final Long authorId, final Accessor accessor) {
            return !studyRoom.isOwner(accessor);
        }
    },
    COMMUNITY("community") {
        @Override
        boolean isUnwritableAccessor(final StudyRoom studyRoom, final Accessor accessor) {
            return !studyRoom.isPermittedAccessor(accessor);
        }

        @Override
        boolean isUneditableAccessor(final StudyRoom studyRoom, final Long authorId, final Accessor accessor) {
            return !(studyRoom.isPermittedAccessor(accessor) && authorId.equals(accessor.getMemberId()));
        }
    };

    private final String typeName;

    ArticleType(final String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static ArticleType from(final String typeName) {
        return Arrays.stream(ArticleType.values())
                .filter(it -> it.typeName.equals(typeName))
                .findAny()
                .orElseThrow(() -> new NotFoundException("존재하지 않는 글 형식입니다."));
    }

    abstract boolean isUnwritableAccessor(final StudyRoom studyRoom, final Accessor accessor);

    abstract boolean isUneditableAccessor(final StudyRoom studyRoom, final Long authorId, final Accessor accessor);
}
