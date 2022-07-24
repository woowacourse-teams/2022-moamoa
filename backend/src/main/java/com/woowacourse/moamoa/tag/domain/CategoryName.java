package com.woowacourse.moamoa.tag.domain;

import com.woowacourse.moamoa.tag.exception.InvalidCategoryNameException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryName {

    GENERATION("generation"),
    AREA("area"),
    SUBJECT("subject");

    private final String name;

    public static CategoryName of(final String name) {
        return Arrays.stream(CategoryName.values())
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .orElseThrow(InvalidCategoryNameException::new);
    }
}
