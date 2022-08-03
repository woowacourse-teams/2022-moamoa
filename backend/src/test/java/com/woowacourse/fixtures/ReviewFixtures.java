package com.woowacourse.fixtures;

import static com.woowacourse.fixtures.MemberFixtures.그린론_응답;
import static com.woowacourse.fixtures.MemberFixtures.디우_응답;
import static com.woowacourse.fixtures.MemberFixtures.베루스_응답;
import static com.woowacourse.fixtures.MemberFixtures.짱구_응답;

import com.woowacourse.moamoa.review.query.data.ReviewData;
import java.time.LocalDate;

public class ReviewFixtures {

    /* 자바 스터디 리뷰 */
    public static final Long 자바_리뷰1_아이디 = 1L;
    public static final String 자바_리뷰1_내용 = "자바 스터디 첫 번째 리뷰입니다.";
    public static final ReviewData 자바_리뷰1 = new ReviewData(자바_리뷰1_아이디, 짱구_응답,
            LocalDate.of(2022, 10, 9), LocalDate.of(2022, 10, 9), 자바_리뷰1_내용);

    public static final Long 자바_리뷰2_아이디 = 2L;
    public static final String 자바_리뷰2_내용 = "자바 스터디 두 번째 리뷰입니다.";
    public static final ReviewData 자바_리뷰2 = new ReviewData(자바_리뷰2_아이디, 베루스_응답,
            LocalDate.of(2022, 10, 9), LocalDate.of(2022, 10, 10), 자바_리뷰2_내용);

    public static final Long 자바_리뷰3_아이디 = 3L;
    public static final String 자바_리뷰3_내용 = "자바 스터디 세 번째 리뷰입니다.";
    public static final ReviewData 자바_리뷰3 = new ReviewData(자바_리뷰3_아이디, 그린론_응답,
            LocalDate.of(2022, 10, 10), LocalDate.of(2022, 10, 10), 자바_리뷰3_내용);

    public static final Long 자바_리뷰4_아이디 = 4L;
    public static final String 자바_리뷰4_내용 = "자바 스터디 네 번째 리뷰입니다.";
    public static final ReviewData 자바_리뷰4 = new ReviewData(자바_리뷰4_아이디, 디우_응답,
            LocalDate.of(2022, 10, 14), LocalDate.of(2022, 10, 15), 자바_리뷰4_내용);

    public static final int 자바_리뷰_총_개수 = 4;
}
