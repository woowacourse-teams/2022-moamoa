package com.woowacourse.fixtures;

import com.woowacourse.moamoa.tag.domain.Category;
import com.woowacourse.moamoa.tag.domain.CategoryName;
import com.woowacourse.moamoa.tag.query.response.CategoryData;

public class CategoryFixtures {

    public static final Long GENERATION_아이디 = 1L;
    public static final CategoryName GENERATION_이름 = CategoryName.GENERATION;
    public static final Category GENERATION = new Category(GENERATION_아이디, GENERATION_이름);
    public static final CategoryData GENERATION_응답 = new CategoryData(GENERATION_아이디, GENERATION_이름.toString());

    public static final Long AREA_아이디 = 2L;
    public static final CategoryName AREA_이름 = CategoryName.AREA;
    public static final Category AREA = new Category(AREA_아이디, AREA_이름);
    public static final CategoryData AREA_응답 = new CategoryData(AREA_아이디, AREA_이름.toString());

    public static final Long SUBJECT_아이디 = 3L;
    public static final CategoryName SUBJECT_이름 = CategoryName.SUBJECT;
    public static final Category SUBJECT = new Category(SUBJECT_아이디, SUBJECT_이름);
    public static final CategoryData SUBJECT_응답 = new CategoryData(SUBJECT_아이디, SUBJECT_이름.toString());
}
