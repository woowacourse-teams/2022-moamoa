package com.woowacourse.moamoa.common.utils;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class DateTimeSystem {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
