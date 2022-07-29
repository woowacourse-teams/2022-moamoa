package com.woowacourse.moamoa.study.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class DateTimeSystem {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
