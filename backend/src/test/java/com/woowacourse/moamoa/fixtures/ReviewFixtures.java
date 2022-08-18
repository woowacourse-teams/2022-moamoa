package com.woowacourse.moamoa.fixtures;

import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import com.woowacourse.moamoa.review.domain.Review;
import com.woowacourse.moamoa.review.domain.Reviewer;

public class ReviewFixtures {

    /* 자바 스터디 리뷰 */
    public static final String 자바_리뷰1_내용 = "자바 스터디 첫 번째 리뷰입니다.";
    public static final String 자바_리뷰2_내용 = "자바 스터디 두 번째 리뷰입니다.";
    public static final String 자바_리뷰3_내용 = "자바 스터디 세 번째 리뷰입니다.";
    public static final String 자바_리뷰4_내용 = "자바 스터디 네 번째 리뷰입니다.";

    public static final String 리액트_리뷰1_내용 = "리액트 스터디 첫 번째 리뷰입니다.";
    public static final String 리액트_리뷰2_내용 = "리액트 스터디 두 번째 리뷰입니다.";
    public static final String 리액트_리뷰3_내용 = "리액트 스터디 세 번째 리뷰입니다.";

    public static Review 자바_리뷰1(final Long studyId, final Long memberId) {
        return 리뷰(studyId, memberId, 자바_리뷰1_내용);
    }

    public static Review 자바_리뷰2(final Long studyId, final Long memberId) {
        return 리뷰(studyId, memberId, 자바_리뷰2_내용);
    }

    public static Review 자바_리뷰3(final Long studyId, final Long memberId) {
        return 리뷰(studyId, memberId, 자바_리뷰3_내용);
    }

    public static Review 자바_리뷰4(final Long studyId, final Long memberId) {
        return 리뷰(studyId, memberId, 자바_리뷰4_내용);
    }

    public static Review 리액트_리뷰1(final Long studyId, final Long memberId) {
        return 리뷰(studyId, memberId, 리액트_리뷰1_내용);
    }

    public static Review 리액트_리뷰2(final Long studyId, final Long memberId) {
        return 리뷰(studyId, memberId, 리액트_리뷰2_내용);
    }

    public static Review 리액트_리뷰3(final Long studyId, final Long memberId) {
        return 리뷰(studyId, memberId, 리액트_리뷰3_내용);
    }

    private static Review 리뷰(final Long studyId, final Long reviewerId, final String content) {
        return new Review(new AssociatedStudy(studyId), new Reviewer(reviewerId), content);
    }
}
