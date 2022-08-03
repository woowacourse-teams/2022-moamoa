package com.woowacourse.fixtures;

import static com.woowacourse.fixtures.CategoryFixtures.AREA_응답;
import static com.woowacourse.fixtures.CategoryFixtures.GENERATION_응답;
import static com.woowacourse.fixtures.CategoryFixtures.SUBJECT_응답;

import com.woowacourse.moamoa.tag.query.response.TagData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;

public class TagFixtures {

    public static final Long 자바_태그_아이디 = 1L;
    public static final String 자바_태그명 = "Java";
    public static final String 자바_태그_설명 = "자바";
    public static final TagData 자바_태그 = new TagData(자바_태그_아이디, 자바_태그명, 자바_태그_설명, SUBJECT_응답);
    public static final TagSummaryData 자바_태그_요약 = new TagSummaryData(자바_태그_아이디, 자바_태그명);

    public static final Long 우테코4기_태그_아이디 = 2L;
    public static final String 우테코4기_태그명 = "4기";
    public static final String 우테코4기_태그_설명 = "우테코4기";
    public static final TagData 우테코4기_태그 = new TagData(우테코4기_태그_아이디, 우테코4기_태그명, 우테코4기_태그_설명, GENERATION_응답);
    public static final TagSummaryData 우테코4기_태그_요약 = new TagSummaryData(우테코4기_태그_아이디, 우테코4기_태그명);

    public static final Long BE_태그_아이디 = 3L;
    public static final String BE_태그명 = "BE";
    public static final String BE_태그_설명 = "백엔드";
    public static final TagData BE_태그 = new TagData(BE_태그_아이디, BE_태그명, BE_태그_설명, AREA_응답);
    public static final TagSummaryData BE_태그_요약 = new TagSummaryData(BE_태그_아이디, BE_태그명);

    public static final Long FE_태그_아이디 = 4L;
    public static final String FE_태그명 = "FE";
    public static final String FE_태그_설명 = "프론트엔드";
    public static final TagData FE_태그 = new TagData(FE_태그_아이디, FE_태그명, FE_태그_설명, AREA_응답);
    public static final TagSummaryData FE_태그_요약 = new TagSummaryData(FE_태그_아이디, FE_태그명);

    public static final Long 리액트_태그_아이디 = 5L;
    public static final String 리액트_태그명 = "React";
    public static final String 리액트_태그_설명 = "리액트";
    public static final TagData 리액트_태그 = new TagData(리액트_태그_아이디, 리액트_태그명, 리액트_태그_설명, SUBJECT_응답);
    public static final TagSummaryData 리액트_태그_요약 = new TagSummaryData(리액트_태그_아이디, 리액트_태그명);
}
